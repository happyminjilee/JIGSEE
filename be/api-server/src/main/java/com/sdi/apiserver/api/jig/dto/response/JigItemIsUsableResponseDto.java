package com.sdi.apiserver.api.jig.dto.response;

import lombok.Value;

@Value
public class JigItemIsUsableResponseDto {
    Boolean isUsable;
    JigItemSummary data;

    @Value
    public static class JigItemSummary{
        Integer useCount;
        String useAccumulationTime;
        Integer repairCount;
    }
}
