package com.sdi.jig.dto.response;

import java.util.List;

public record FacilityItemNeedToInspectionResponseDto(
        List<FacilityItemInfo> list
) {

    public static FacilityItemNeedToInspectionResponseDto from(List<FacilityItemInfo> facilityItemInfos){
        return new FacilityItemNeedToInspectionResponseDto(facilityItemInfos);
    }

    public record FacilityItemInfo(
            Long id,
            String facilitySerialNo
    ){
        public static FacilityItemInfo of(Long id, String facilitySerialNo){
            return new FacilityItemInfo(id, facilitySerialNo);
        }
    }
}
