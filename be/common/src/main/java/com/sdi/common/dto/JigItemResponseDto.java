package com.sdi.common.dto;

import com.sdi.common.util.JigStatus;

public record JigItemResponseDto(
        Long id,
        String model,
        String serialNo,
        JigStatus status,
        String expectLife,
        Integer useCount,
        String useAccumulationTime,
        Integer repairCount
) {
}
