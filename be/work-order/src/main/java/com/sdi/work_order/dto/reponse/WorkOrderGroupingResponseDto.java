package com.sdi.work_order.dto.reponse;

import com.sdi.work_order.util.WorkOrderItem;
import com.sdi.work_order.util.WorkOrderStatus;

import java.util.List;
import java.util.Map;

public record WorkOrderGroupingResponseDto(
        List<WorkOrderItem> publish,
        List<WorkOrderItem> progress,
        List<WorkOrderItem> finish

) {

    public static WorkOrderGroupingResponseDto from(Map<WorkOrderStatus, List<WorkOrderItem>> group){
        return new WorkOrderGroupingResponseDto(
            group.get(WorkOrderStatus.PUBLISH),
            group.get(WorkOrderStatus.PROGRESS),
            group.get(WorkOrderStatus.FINISH)
        );
    }
}
