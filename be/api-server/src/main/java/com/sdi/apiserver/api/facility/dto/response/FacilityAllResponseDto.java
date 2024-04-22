package com.sdi.apiserver.api.facility.dto.response;

import lombok.Value;

import java.util.List;

@Value
public class FacilityAllResponseDto {

    List<FacilityInfo> list;

    @Value
    public static class FacilityInfo{
        Long id;
        String alias;
        String model;
        String facilitySerialNo;
    }
}
