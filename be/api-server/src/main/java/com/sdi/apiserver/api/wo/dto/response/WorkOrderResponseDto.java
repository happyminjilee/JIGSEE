package com.sdi.apiserver.api.wo.dto.response;

import com.sdi.apiserver.api.wo.dto.response.util.WorkOrderSummary;

import java.util.List;

public record WorkOrderResponseDto(
        Integer currentPage,
        Integer endPage,
        List<WorkOrderSummary> list
) {

}
