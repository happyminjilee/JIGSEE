package com.sdi.apiserver.api.wo.response;

import com.sdi.apiserver.api.wo.response.util.WorkOrderSummary;

import java.util.List;

public record WorkOrderResponseDto(
        List<WorkOrderSummary> list
) {

    public static WorkOrderResponseDto from(List<WorkOrderSummary> list){
        return new WorkOrderResponseDto(list);
    }
}
