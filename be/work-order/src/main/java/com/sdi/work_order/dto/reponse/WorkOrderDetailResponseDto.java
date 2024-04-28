package com.sdi.work_order.dto.reponse;

import com.sdi.work_order.util.WorkOrderStatus;

import java.time.LocalDateTime;

public record WorkOrderDetailResponseDto(
        Long id,
        WorkOrderStatus status,
        String creator,
        String terminator,
        String model,
        String serialNo,
        LocalDateTime createdAt

) {
}
