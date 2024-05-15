package com.sdi.jig.application;

import com.sdi.jig.client.NotificationApiClient;
import com.sdi.jig.client.NotificationClient;
import com.sdi.jig.client.WorkOrderClient;
import com.sdi.jig.dto.request.JigItemAcceptRequestDto;
import com.sdi.jig.dto.request.JigItemInventoryRequestDto;
import com.sdi.jig.dto.request.NotificationFcmInspectionRequestDto;
import com.sdi.jig.dto.request.WorkOrderAutoCreateRequestDto;
import com.sdi.jig.dto.response.JigItemFacilityAvailableResponseDto;
import com.sdi.jig.dto.response.JigItemIsUsableResponseDto;
import com.sdi.jig.dto.response.JigItemIsUsableResponseDto.JigItemSummary;
import com.sdi.jig.dto.response.JigItemResponseDto;
import com.sdi.jig.entity.nosql.JigNosqlEntity;
import com.sdi.jig.entity.rdb.*;
import com.sdi.jig.repository.rdb.*;
import com.sdi.jig.util.IOStatus;
import com.sdi.jig.util.JigStatus;
import com.sdi.jig.util.TimeCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sdi.jig.dto.request.JigItemAddRequestDto.JigAddRequest;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JigItemService {

    private final JigService jigService;
    private final JigItemRDBRepository jigItemRDBRepository;
    private final FacilityRDBRepository facilityRDBRepository;
    private final FacilityItemRDBRepository facilityItemRDBRepository;
    private final JigItemIOHistoryRepository jigItemIOHistoryRepository;
    private final FacilityJigMappingRDBRepository facilityJigMappingRDBRepository;
    private final JigItemRepairHistoryRepository jigItemRepairHistoryRepository;
    private final JigItemInspectionRDBRepository jigItemInspectionRDBRepository;
    private final NotificationClient notificationClient;
    private final NotificationApiClient notificationApiClient;
    private final WorkOrderClient workOrderClient;

    public JigItemResponseDto findBySerialNo(String serialNo) {
        JigItemRDBEntity rdb = getJigItemBySerialNo(serialNo);
        JigNosqlEntity nosql = jigService.getJigNosqlEntityByModel(rdb.getJig().getModel());
        return JigItemResponseDto.from(
                rdb,
                nosql,
                getUseCount(rdb.getId()),
                getUseAccumulationTime(rdb.getUseAccumulateTime()),
                getRepairCount(rdb.getId())
        );
    }

    public JigItemResponseDto findBySerialNoIncludeDelete(String serialNo) {
        JigItemRDBEntity rdb = getJigItemIncludeDeleteBySerialNo(serialNo);
        JigNosqlEntity nosql = jigService.getJigNosqlEntityByModel(rdb.getJig().getModel());
        return JigItemResponseDto.from(
                rdb,
                nosql,
                getUseCount(rdb.getId()),
                getUseAccumulationTime(rdb.getUseAccumulateTime()),
                getRepairCount(rdb.getId())
        );
    }

    @Transactional
    public void add(List<JigAddRequest> list) {
        List<JigItemRDBEntity> datas = new ArrayList<>();

        for (JigAddRequest request : list) {
            String model = request.model();
            JigRDBEntity jigRdbEntityByModel = jigService.getJigRdbEntityByModel(model);

            for (String serialNo : request.serialNos()) {
                datas.add(JigItemRDBEntity.from(serialNo, jigRdbEntityByModel));
            }
        }

        jigItemRDBRepository.saveAll(datas);
    }

    public JigItemIsUsableResponseDto isUsable(String facilitySerialNo, String jigSerialNo) {
        FacilityItemRDBEntity facilityItemBySerialNo = getFacilityItemBySerialNo(facilitySerialNo);
        FacilityRDBEntity facilityByModel = facilityItemBySerialNo.getFacility();
        JigItemRDBEntity jigItem = getJigItemBySerialNo(jigSerialNo);
        Long jigId = jigItem.getJig().getId();

        boolean isUsable = isUsable(jigItem, facilityByModel, jigId);

        return JigItemIsUsableResponseDto.from(isUsable,
                JigItemSummary.from(
                        getUseCount(jigItem.getId()),
                        getUseAccumulationTime(jigItem.getUseAccumulateTime()),
                        getRepairCount(jigItem.getId()
                        )
                )
        );
    }

    @Transactional
    public void deleteBySerialNo(String serialNo) {
        JigItemRDBEntity jigItem = getJigItemBySerialNo(serialNo);
        jigItem.delete();
    }

    @Transactional
    public void updateStateBySerialNo(String serialNo, JigStatus status) {
        JigItemRDBEntity jigItem = getJigItemBySerialNo(serialNo);
        jigItem.updateState(status);
    }

    @Transactional
    public void exchangeBySerialNo(String facilitySerialNo, String beforeSerialNo, String afterSerialNo) {
        FacilityItemRDBEntity facilityItem = getFacilityItemBySerialNo(facilitySerialNo);
        JigItemRDBEntity beforeJigItem = getJigItemBySerialNo(beforeSerialNo);
        JigItemRDBEntity afterJigItem = getJigItemBySerialNo(afterSerialNo);

        updateBecauseExchange(facilityItem, beforeJigItem, afterJigItem);

        // wo 생성 요청
        workOrderClient.auto(WorkOrderAutoCreateRequestDto.from(List.of(beforeJigItem.getSerialNo())));
    }

    @Transactional
    public void recoveryBySerialNo(String serialNo) {
        JigItemRDBEntity jigItem = getDeletedJigItemBySerialNo(serialNo);
        jigItem.recovery();
    }

    public JigItemFacilityAvailableResponseDto facilityAvailable(String facilityModel) {
        FacilityRDBEntity facilityByModel = getFacilityByModel(facilityModel);
        List<Long> jigIds = extractJigIds(facilityByModel);

        List<String> serialNos = getAvailableExtractJigItemSerialNos(jigIds);

        return JigItemFacilityAvailableResponseDto.from(serialNos);
    }

    @Transactional
    public void jigItemInspection(List<Long> jigItemIds) {
        List<JigItemRDBEntity> bySerialNoIn = jigItemRDBRepository.findAllById(jigItemIds);
        List<JigItemInspectionRDBEntity> datas = new ArrayList<>();
        List<String> newJigInspectionSerialNos = new ArrayList<>();
        String notificationId = UUID.randomUUID().toString();

        for (JigItemRDBEntity jigItemRDBEntity : bySerialNoIn) {
            // 아직 점검 리스트에 포함되지 않은 지그일 경우 데이터 추가 -> 중복 방지
            boolean empty = jigItemInspectionRDBRepository.findByIsInspectionFalseAndJigItemId(jigItemRDBEntity.getId())
                    .isEmpty();
            if (empty) {
                newJigInspectionSerialNos.add(jigItemRDBEntity.getSerialNo());
                datas.add(JigItemInspectionRDBEntity.of(jigItemRDBEntity, notificationId));
            }
        }

        // 데이터 저장
        jigItemInspectionRDBRepository.saveAll(datas);

        // 알람 전송
        sendToNotification(notificationId, newJigInspectionSerialNos);
    }

    private void sendToNotification(String notificationId, List<String> newJigInspectionSerialNos) {
        if (!newJigInspectionSerialNos.isEmpty()) {
            NotificationFcmInspectionRequestDto dto = NotificationFcmInspectionRequestDto.from(notificationId, newJigInspectionSerialNos);
            notificationClient.inspection(dto);
        }
    }

    public List<FacilityItemRDBEntity> getNeedToInspectionFacilityItems() {
        List<JigItemInspectionRDBEntity> jigItemInspection = jigItemInspectionRDBRepository.findByIsInspectionFalse();
        return jigItemInspection.stream()
                .filter(j -> j.getJigItem().getFacilityItem() != null) // 점검 대상 설비 조회에서 쓰이기 때문에, 현재 설비에 투입된 지그 정보가 아니면 제외
                .map(j -> j.getJigItem().getFacilityItem())
                .toList();
    }

    public List<String> getNeedToInspectionFacilityJigItems(Long facilityId) {
        List<JigItemInspectionRDBEntity> needToInspectionJigItems = jigItemInspectionRDBRepository.findByIsInspectionFalse();
        List<JigItemInspectionRDBEntity> filterByFacilityId = needToInspectionJigItems.stream()
                .filter(j -> j.getJigItem().getFacilityItem() != null)
                .filter(i -> i.getJigItem().getFacilityItem().getId().equals(facilityId))
                .toList();
        return filterByFacilityId.stream()
                .map(f -> f.getJigItem().getSerialNo())
                .toList();
    }

    @Transactional
    public void accept(String accessToken, JigItemAcceptRequestDto dto) {
        if (dto.isAccept()) {
            List<JigItemRDBEntity> bySerialNoIn = jigItemRDBRepository.findBySerialNoIn(dto.serialNos());
            for (JigItemRDBEntity jigItemRDBEntity : bySerialNoIn) {
                jigItemRDBEntity.updateState(JigStatus.READY);
            }
        }

        notificationApiClient.accept(accessToken, dto);
    }

    public JigItemInventoryRequestDto inventory() {
        List<JigItemRDBEntity> byStatus = jigItemRDBRepository.findByStatus(JigStatus.WAREHOUSE);
        Map<String, List<JigItemRDBEntity>> groupByModel = byStatus.stream()
                .collect(Collectors.groupingBy(e -> e.getJig().getModel()));

        return JigItemInventoryRequestDto.from(groupByModel);
    }

    private List<Long> extractJigIds(FacilityRDBEntity facilityByModel) {
        return getFacilityJigMappingByFacilityId(facilityByModel.getId())
                .stream()
                .map(m -> m.getJig().getId())
                .toList();
    }

    private List<String> getAvailableExtractJigItemSerialNos(List<Long> jigIds) {
        return jigItemRDBRepository.findByStatusAndJigIdIn(JigStatus.WAREHOUSE, jigIds)
                .stream()
                .map(JigItemRDBEntity::getSerialNo)
                .toList();
    }

    private void updateBecauseExchange(FacilityItemRDBEntity facilityItem, JigItemRDBEntity beforeJigItem, JigItemRDBEntity afterJigItem) {
        // 같은 지그 모델인지 확인
        isNotSameModelAndThrow(beforeJigItem.getJig().getId(), afterJigItem.getJig().getId());

        // 지그 교체
        facilityItem.exchangeJigItem(beforeJigItem, afterJigItem);

        // 지그에 설비 정보 추가
        beforeJigItem.updateFacilityItemNull();
        afterJigItem.updateFacilityItem(facilityItem);

        // 지그 상태 변경
        beforeJigItem.updateState(JigStatus.OUT);
        afterJigItem.updateState(JigStatus.IN);

        // 누적 시간 갱신
        beforeJigItem.addAccumulateTime(getRecentIn(beforeJigItem.getId()));

        // io 이력 추가
        jigItemIOHistoryRepository.save(JigItemIOHistoryRDBEntity.of(IOStatus.OUT, beforeJigItem));
        jigItemIOHistoryRepository.save(JigItemIOHistoryRDBEntity.of(IOStatus.IN, afterJigItem));

        // 지그 점검(inspection) 완료 상태로 변경
        jigItemInspection(beforeJigItem.getId());
    }

    private void isNotSameModelAndThrow(Long beforeJigModeId, Long afterJigModelId) {
        if(!beforeJigModeId.equals(afterJigModelId)){
            throw new IllegalArgumentException("같은 모델이 아니어서 교체할 수 없습니다.");
        }
    }

    private void jigItemInspection(Long id) {
        try {
            JigItemInspectionRDBEntity jigItemInspection = getByJigItemIdInInspection(id);
            jigItemInspection.updateIsInspection();
        } catch (IllegalArgumentException e) {
            log.warn("시스템에서 점검 항목을 만들지 않은 \'{}\'를 교체 했습니다.", id);
        }
    }

    private boolean isUsable(JigItemRDBEntity jigItem, FacilityRDBEntity facilityByModel, Long jigId) {
        // 1. 지그 사용대기 상태 판단
        if (isReady(jigItem.getStatus())) {
            // 2. 설비와 지그 일치
            return isMatchFacilityAndJig(facilityByModel.getId(), jigId);
        }
        return false;
    }

    private boolean isReady(JigStatus status) {
        return (status == JigStatus.READY);
    }

    private boolean isMatchFacilityAndJig(Long facilityId, Long jigId) {
        List<FacilityJigMappingRDBEntity> list = getFacilityJigMappingByFacilityId(facilityId);
        return checkContain(list, jigId);
    }

    private boolean checkContain(List<FacilityJigMappingRDBEntity> list, Long jigId) {
        return list.stream()
                .anyMatch(e -> e.getJig().getId().equals(jigId));
    }

    private List<FacilityJigMappingRDBEntity> getFacilityJigMappingByFacilityId(Long facilityId) {
        return facilityJigMappingRDBRepository.findByFacilityId(facilityId);
    }

    private FacilityRDBEntity getFacilityByModel(String facilityModel) {
        return facilityRDBRepository.findByModel(facilityModel)
                .orElseThrow(() -> new IllegalArgumentException("model에 알맞는 FACILITY 정보를 찾을 수 없습니다."));
    }

    private JigItemRDBEntity getJigItemBySerialNo(String jigSerialNo) {
        return jigItemRDBRepository.findBySerialNoAndIsDeleteFalse(jigSerialNo)
                .orElseThrow(() -> new IllegalArgumentException("serial 번호로 JIG ITEM을 찾을 수 없습니다."));
    }

    private JigItemRDBEntity getDeletedJigItemBySerialNo(String jigSerialNo) {
        return jigItemRDBRepository.findBySerialNoAndIsDeleteTrue(jigSerialNo)
                .orElseThrow(() -> new IllegalArgumentException("serial 번호로 JIG ITEM을 찾을 수 없습니다."));
    }

    private JigItemRDBEntity getJigItemIncludeDeleteBySerialNo(String jigSerialNo) {
        return jigItemRDBRepository.findBySerialNo(jigSerialNo)
                .orElseThrow(() -> new IllegalArgumentException("serial 번호로 JIG ITEM을 찾을 수 없습니다."));
    }

    private FacilityItemRDBEntity getFacilityItemBySerialNo(String facilitySerialNo) {
        return facilityItemRDBRepository.findBySerialNo(facilitySerialNo)
                .orElseThrow(() -> new IllegalArgumentException("serial 번호로 FACILITY ITEM을 찾을 수 없습니다."));
    }

    private JigItemIOHistoryRDBEntity getRecentIn(Long jigItemId) {
        return jigItemIOHistoryRepository.findFirstByJigItemIdAndStatusOrderByInOutTime(jigItemId, IOStatus.IN)
                .orElseThrow(() -> new IllegalArgumentException(String.format("\'%d\'의 지그 투입 이력이 없습니다.", jigItemId)));
    }

    private JigItemInspectionRDBEntity getByJigItemIdInInspection(Long id) {
        return jigItemInspectionRDBRepository.findByJigItemIdAndIsInspectionFalse(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Jig Item Id = \'%d\'를 Inspection 항목에서 알맞은 지그를 찾을 수 없습니다.", id)));
    }

    private List<JigItemRepairHistoryRDBEntity> getJigItemRepairHistoriesByJigItemId(Long jigItemId) {
        return jigItemRepairHistoryRepository.findByJigItemId(jigItemId);
    }

    private Integer getUseCount(Long jigItemId) {
        return jigItemIOHistoryRepository.findByJigItemIdAndStatus(jigItemId, IOStatus.IN).size();
    }

    private String getUseAccumulationTime(Long jigItemUseAccumulateTime) {
        return TimeCalculator.millsToString(jigItemUseAccumulateTime);
    }

    private Integer getRepairCount(Long jigItemId) {
        return getJigItemRepairHistoriesByJigItemId(jigItemId).size();
    }
}
