package com.sdi.apiserver.api.facility.dto.request;

import lombok.Value;

import java.util.List;

@Value
public class FacilityCreateRequestDto {

    String alias;
    Long facilityId;
    List<String> jigs;
}
