package com.sdi.jig.application;

import com.sdi.jig.dto.response.FacilityItemAllResponseDto;
import com.sdi.jig.dto.response.FacilityItemAllResponseDto.FacilityItemSummary;
import com.sdi.jig.dto.response.FacilityItemDetailResponseDto;
import com.sdi.jig.dto.response.JigItemResponseDto;
import com.sdi.jig.entity.FacilityItemRDBEntity;
import com.sdi.jig.repository.FacilityItemRDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private FacilityItemRDBEntity getFacilityItem(Long facilityId) {
        return facilityItemRDBRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("\'%d\'로 facility item을 찾을 수 없습니다.", facilityId)));
    }
}
