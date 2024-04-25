package com.sdi.jig.dto.response;

public record JigItemIsUsableResponseDto(
        Boolean isUsable,
        JigItemSummary data
) {

    public static JigItemIsUsableResponseDto from(Boolean isUsable, JigItemSummary jigItemSummary){
        return new JigItemIsUsableResponseDto(isUsable, jigItemSummary);
    }

    public record JigItemSummary(
            String facilityModel,
            String jigUseAccumulationTime,
            Integer repairCount
    ){
        public static JigItemSummary from(String facilityModel,
                                          String jigUseAccumulationTime,
                                          Integer repairCount){
            return new JigItemSummary(facilityModel, jigUseAccumulationTime, repairCount);
        }
    }
}
