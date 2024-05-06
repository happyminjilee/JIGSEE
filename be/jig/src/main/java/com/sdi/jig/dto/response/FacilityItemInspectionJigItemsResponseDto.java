package com.sdi.jig.dto.response;

import java.util.List;

public record FacilityItemInspectionJigItemsResponseDto(
    List<String> serialNos
) {
    public static FacilityItemInspectionJigItemsResponseDto from(List<String> serialNos){
        return new FacilityItemInspectionJigItemsResponseDto(serialNos);
    }
}
