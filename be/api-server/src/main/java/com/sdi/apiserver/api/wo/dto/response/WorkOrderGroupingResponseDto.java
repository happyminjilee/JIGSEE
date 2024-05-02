package com.sdi.apiserver.api.wo.dto.response;

import com.sdi.apiserver.api.wo.dto.response.util.WorkOrderSummary;

import java.util.List;

public record WorkOrderGroupingResponseDto(
        List<WorkOrderSummary> publish,
        List<WorkOrderSummary> progress,
        List<WorkOrderSummary> finish
) {

    public static WorkOrderGroupingResponseDto from(List<WorkOrderSummary> publish,
                                                    List<WorkOrderSummary> progress,
                                                    List<WorkOrderSummary> finish){
        return new WorkOrderGroupingResponseDto(publish, progress, finish);
    }
}
