package com.sdi.jig.dto.response;

public record JigItemIsUsableResponseDto(
        Boolean isUsable,
        JigItemSummary data
) {

    public static JigItemIsUsableResponseDto from(Boolean isUsable, JigItemSummary jigItemSummary){
        return new JigItemIsUsableResponseDto(isUsable, jigItemSummary);
    }

    public record JigItemSummary(
            Integer useCount,
            String useAccumulationTime,
            Integer repairCount
    ){
        public static JigItemSummary from(Integer useCount,
                                          String useAccumulationTime,
                                          Integer repairCount){
            return new JigItemSummary(useCount, useAccumulationTime, repairCount);
        }
    }
}
