package com.sdi.work_order.dto.request;

import com.sdi.work_order.util.WorkOrderCheckList;

import java.util.List;

public record WorkOrderSaveRequestDto(
        String id,
        List<WorkOrderCheckList> checkList
) {
}
