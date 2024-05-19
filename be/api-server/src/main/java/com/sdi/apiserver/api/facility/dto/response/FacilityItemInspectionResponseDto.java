package com.sdi.apiserver.api.facility.dto.response;

import java.util.List;

public record FacilityItemInspectionResponseDto(
        List<FacilityInspection> list
) {

    public record FacilityInspection(Long id,
                                     String facilitySerialNo){

    }
}
