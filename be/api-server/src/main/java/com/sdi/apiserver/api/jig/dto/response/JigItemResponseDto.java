package com.sdi.apiserver.api.jig.dto.response;

import com.sdi.apiserver.util.CheckItem;

import java.util.List;


public record JigItemResponseDto(
        Long id,
        String model,
        String serialNo,
        String status,
        String expectLife,
        Integer useCount,
        String useAccumulationTime,
        Integer repairCount,
        List<CheckItem> checkList
) {
}
