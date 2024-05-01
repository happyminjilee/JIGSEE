package com.sdi.jig.dto.response;

import com.sdi.jig.entity.FacilityItemRDBEntity;

import java.util.List;

public record FacilityItemDetailResponseDto(
        Long id,
        String model,
        String facilitySerialNo,
        List<JigItemResponseDto> list
) {

    public static FacilityItemDetailResponseDto from(FacilityItemRDBEntity facilityItem, List<JigItemResponseDto> list){
        return new FacilityItemDetailResponseDto(
                facilityItem.getId(),
                facilityItem.getFacility().getModel(),
                facilityItem.getSerialNo(),
                list
        );

    }
}
