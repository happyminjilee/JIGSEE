package com.sdi.apiserver.api.jig.dto.response;

public record JigItemIsUsableResponseDto(
        Boolean isUsable,
        JigItemSummary data
) {


    public record JigItemSummary(
            Integer useCount,
            String useAccumulationTime,
            Integer repairCount
    ) {

    }
}
