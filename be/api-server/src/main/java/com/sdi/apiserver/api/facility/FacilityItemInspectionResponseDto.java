package com.sdi.apiserver.api.facility;

import java.util.List;

public record FacilityItemInspectionResponseDto(
        List<FacilityInspection> list
) {

    public record FacilityInspection(Long id,
                                     String serialNo){

    }
}
