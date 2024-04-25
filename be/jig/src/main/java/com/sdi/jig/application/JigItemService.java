package com.sdi.jig.application;

import com.sdi.jig.dto.response.JigItemIsUsableResponseDto;
import com.sdi.jig.dto.response.JigItemIsUsableResponseDto.JigItemSummary;
import com.sdi.jig.entity.*;
import com.sdi.jig.repository.*;
import com.sdi.jig.util.IOStatus;
import com.sdi.jig.util.JigStatus;
import com.sdi.jig.util.TimeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.sdi.jig.dto.request.JigItemAddRequestDto.JigAddRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JigItemService {

    private final JigService jigService;
    private final TimeCalculator timeCalculator;
    private final JigItemRDBRepository jigItemRDBRepository;
    private final FacilityRDBRepository facilityRDBRepository;
    private final FacilityJigMappingRDBRepository facilityJigMappingRDBRepository;
    private final JigItemIOHistoryRepository jigItemIOHistoryRepository;
    private final JigItemRepairHistoryRepository jigItemRepairHistoryRepository;

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

    public JigItemIsUsableResponseDto isUsable(String facilityModel, String jigSerialNo) {
        FacilityRDBEntity facilityByModel = getFacilityByModel(facilityModel);
        JigItemRDBEntity jigItem = getJigItemBySerialNo(jigSerialNo);
        Long jigId = jigItem.getJig().getId();

        boolean isUsable = isUsable(jigItem, facilityByModel, jigId);

        int useCount = jigItemIOHistoryRepository.findByJigItemIdAndStatus(jigItem.getId(), IOStatus.IN).size();
        String useAccumulationTime = timeCalculator.millsToString(jigItem.getUseAccumulateTime());
        int repairCount = getJigItemRepairHistoriesByJigItemId(jigItem.getId()).size();

        return JigItemIsUsableResponseDto.from(isUsable,
                JigItemSummary.from(
                        useCount,
                        useAccumulationTime,
                        repairCount
                ));
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
                .filter(e -> e.getJig().getId().equals(jigId))
                .findAny()
                .isEmpty();
    }

    private List<FacilityJigMappingRDBEntity> getFacilityJigMappingByFacilityId(Long facilityId) {
        return facilityJigMappingRDBRepository.findByFacilityId(facilityId);
    }

    private FacilityRDBEntity getFacilityByModel(String facilityModel) {
        return facilityRDBRepository.findByModel(facilityModel)
                .orElseThrow(() -> new IllegalArgumentException("model에 알맞는 FACILITY 정보를 찾을 수 없습니다."));
    }

    private JigItemRDBEntity getJigItemBySerialNo(String jigSerialNo) {
        return jigItemRDBRepository.findBySerialNo(jigSerialNo)
                .orElseThrow(() -> new IllegalArgumentException("serial 번호로 JIG ITEM을 찾을 수 없습니다."));
    }

    private List<JigItemRepairHistoryRDBEntity> getJigItemRepairHistoriesByJigItemId(Long jigItemId) {
        return jigItemRepairHistoryRepository.findByJigItemId(jigItemId);
    }
}
