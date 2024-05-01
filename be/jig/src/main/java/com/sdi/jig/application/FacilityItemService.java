package com.sdi.jig.application;

import com.sdi.jig.dto.response.FacilityItemAllResponseDto;
import com.sdi.jig.dto.response.FacilityItemAllResponseDto.FacilityItemSummary;
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

    private final FacilityItemRDBRepository facilityItemRDBRepository;

    public FacilityItemAllResponseDto all() {
        List<FacilityItemRDBEntity> all = facilityItemRDBRepository.findAll();
        List<FacilityItemSummary> list = all.stream()
                .map(FacilityItemSummary::from)
                .toList();
        return FacilityItemAllResponseDto.from(list);
    }
}
