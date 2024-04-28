package com.sdi.work_order.dto.reponse;

import com.sdi.work_order.util.WorkOrderItem;

import java.util.List;

public record WorkOrderResponseDto(
        List<WorkOrderItem> list
) {


}
