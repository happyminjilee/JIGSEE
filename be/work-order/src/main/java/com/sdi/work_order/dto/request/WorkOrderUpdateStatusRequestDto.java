package com.sdi.work_order.dto.request;

import com.sdi.work_order.util.WorkOrderStatus;

import java.util.List;

public record WorkOrderUpdateStatusRequestDto(
        List<UpdateStatusItem> list
) {

    public record UpdateStatusItem(
            Long id,
            WorkOrderStatus status
    ) {
    }
}
