package com.sdi.apiserver.api.facility.dto.response;

import java.util.List;


public record FacilityAllResponseDto(
    List<FacilityInfo> list
) {



    public record FacilityInfo(
        Long id,
        String model,
        List<String> jigModels,
        String facilitySerialNo,
        List<String> jigItemSerialNos
    ){
    }
}
