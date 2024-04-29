package com.sdi.work_order.dto.request;

import com.mysql.cj.log.Log;
import com.sdi.work_order.util.WorkOrderCheckList;

import java.util.List;

public record WorkOrderSaveRequestDto(
        Long id,
        List<WorkOrderCheckList> checkList
) {
}
