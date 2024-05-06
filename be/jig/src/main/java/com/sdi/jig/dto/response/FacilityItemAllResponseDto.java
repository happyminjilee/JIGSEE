package com.sdi.jig.dto.response;

import com.sdi.jig.entity.rdb.FacilityItemRDBEntity;
import com.sdi.jig.entity.rdb.JigItemRDBEntity;

import java.util.List;

public record FacilityItemAllResponseDto(
        List<FacilityItemSummary> list
) {

    public static FacilityItemAllResponseDto from(List<FacilityItemSummary> list){
        return new FacilityItemAllResponseDto(list);
    }

    public record FacilityItemSummary(
            Long id,
            String model,
            String facilitySerialNo,
            List<String> jigItemSerialNos
    ){
        public static FacilityItemSummary from(FacilityItemRDBEntity rdb){
            List<String> jigItemSerialNos = rdb.getJigItems()
                    .stream().map(JigItemRDBEntity::getSerialNo)
                    .toList();

            return new FacilityItemSummary(rdb.getId(), rdb.getFacility().getModel(), rdb.getSerialNo(), jigItemSerialNos);
        }
    }
}
