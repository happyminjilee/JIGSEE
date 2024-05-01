package com.sdi.work_order.dto.request;

import com.sdi.work_order.util.WorkOrderCheckItem;

import java.util.List;

public record WorkOrderSaveRequestDto(
        Long id,
        List<WorkOrderCheckItem> checkList
) {
}
