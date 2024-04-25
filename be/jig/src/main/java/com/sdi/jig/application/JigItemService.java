package com.sdi.jig.application;

import com.sdi.jig.dto.response.JigItemIsUsableResponseDto;
import com.sdi.jig.entity.FacilityJigMappingRDBEntity;
import com.sdi.jig.entity.FacilityRDBEntity;
import com.sdi.jig.entity.JigItemRDBEntity;
import com.sdi.jig.entity.JigRDBEntity;
import com.sdi.jig.repository.FacilityJigMappingRDBRepository;
import com.sdi.jig.repository.FacilityRDBRepository;
import com.sdi.jig.repository.JigItemRDBRepository;
import com.sdi.jig.util.JigStatus;
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
    private final JigItemRDBRepository jigItemRDBRepository;
    private final FacilityRDBRepository facilityRDBRepository;
    private final FacilityJigMappingRDBRepository facilityJigMappingRDBRepository;

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

    public JigItemIsUsableResponseDto isUsableOrThrow(String facilityModel, String jigSerialNo) {
        FacilityRDBEntity facilityByModel = getFacilityByModel(facilityModel);
        JigItemRDBEntity jigItem = getJigItemBySerialNo(jigSerialNo);
        Long jigId = jigItem.getJig().getId();

        isUsableOrThrow(jigItem, facilityByModel, jigId);



        return null;
    }

    private void isUsableOrThrow(JigItemRDBEntity jigItem, FacilityRDBEntity facilityByModel, Long jigId) {
        // 1. 지그 사용대기 상태 판단
        isReady(jigItem.getStatus());

        // 2. 설비와 지그 일치
        isMatchFacilityAndJig(facilityByModel.getId(), jigId);
    }

    private void isReady(JigStatus status) {
        if(status != JigStatus.READY){
            throw new IllegalArgumentException("JIG의 상태가 사용대기가 아니라서 사용할 수 없습니다.");
        }
    }

    private void isMatchFacilityAndJig(Long facilityId, Long jigId) {
        List<FacilityJigMappingRDBEntity> list = getFacilityJigMappingByFacilityId(facilityId);
        checkContain(list, jigId);
    }

    private void checkContain(List<FacilityJigMappingRDBEntity> list, Long jigId) {
        list.stream()
                .filter(e -> e.getJig().getId().equals(jigId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("사용 불가능한 지그입니다."));
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
}
