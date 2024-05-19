package com.sdi.notificationapi.dto;

import com.sdi.notificationapi.util.JigStatus;

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
