package com.sdi.apiserver.api.facility.dto.response;

import com.sdi.apiserver.api.jig.dto.util.JigStatus;
import com.sdi.apiserver.util.CheckType;
import lombok.Value;

import java.util.List;

@Value
public class FacilityDetailResponseDto {
    Long id;
    String model;
    String serialNo;
    List<JigDetail> jigList;

    @Value
    public static class JigDetail{
        Long id;
        String model;
        String serialNo;
        JigStatus status;
        String expectLife;
        Integer checkType;
        Integer repairCount;
        Integer checkCount;
    }
}
