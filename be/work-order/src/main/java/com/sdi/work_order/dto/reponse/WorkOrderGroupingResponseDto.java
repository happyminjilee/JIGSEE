package com.sdi.work_order.dto.reponse;

import com.sdi.work_order.util.WorkOrderItem;

import java.util.List;

public record WorkOrderGroupingResponseDto(
        List<WorkOrderItem> publish,
        List<WorkOrderItem> progress,
        List<WorkOrderItem> finish

) {
}
