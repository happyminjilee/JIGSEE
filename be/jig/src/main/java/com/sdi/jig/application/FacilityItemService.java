package com.sdi.jig.application;

import com.sdi.jig.dto.response.FacilityItemAllResponseDto;
import com.sdi.jig.dto.response.FacilityItemAllResponseDto.FacilityItemSummary;
import com.sdi.jig.dto.response.FacilityItemDetailResponseDto;
import com.sdi.jig.dto.response.FacilityItemNeedToInspectionResponseDto;
import com.sdi.jig.dto.response.FacilityItemNeedToInspectionResponseDto.FacilityItemInfo;
import com.sdi.jig.dto.response.JigItemResponseDto;
import com.sdi.jig.entity.rdb.FacilityItemRDBEntity;
import com.sdi.jig.repository.rdb.FacilityItemRDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FacilityItemService {

    private final JigItemService jigItemService;
    private final FacilityItemRDBRepository facilityItemRDBRepository;


    public FacilityItemAllResponseDto all() {
        List<FacilityItemRDBEntity> all = facilityItemRDBRepository.findAll();
        List<FacilityItemSummary> list = all.stream()
                .map(FacilityItemSummary::from)
                .toList();
        return FacilityItemAllResponseDto.from(list);
    }

    public FacilityItemDetailResponseDto detail(Long facilityId) {
        FacilityItemRDBEntity facilityItem = getFacilityItem(facilityId);
        List<JigItemResponseDto> list = facilityItem.getJigItems().stream()
                .map(j -> jigItemService.findBySerialNo(j.getSerialNo()))
                .toList();

        return FacilityItemDetailResponseDto.from(facilityItem, list);
    }

    public FacilityItemNeedToInspectionResponseDto inspection() {
        List<FacilityItemRDBEntity> facilityItems = jigItemService.getNeedToInspectionFacilityItems();
        List<FacilityItemInfo> facilityItemInfos = getFacilityInfos(facilityItems);

        return FacilityItemNeedToInspectionResponseDto.from(facilityItemInfos);
    }

    private static List<FacilityItemInfo> getFacilityInfos(List<FacilityItemRDBEntity> facilityItems) {
        ConcurrentHashMap<Long, String> map = new ConcurrentHashMap<>();
        for (FacilityItemRDBEntity facilityItem : facilityItems) {
            map.put(facilityItem.getId(), facilityItem.getSerialNo());
        }

        return map.keySet().stream()
                .map(key -> FacilityItemInfo.of(key, map.get(key)))
                .toList();
    }

    private FacilityItemRDBEntity getFacilityItem(Long facilityId) {
        return facilityItemRDBRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("\'%d\'로 facility item을 찾을 수 없습니다.", facilityId)));
    }
}
