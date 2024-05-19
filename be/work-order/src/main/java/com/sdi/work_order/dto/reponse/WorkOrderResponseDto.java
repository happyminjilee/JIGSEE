package com.sdi.work_order.dto.reponse;

import com.sdi.work_order.util.WorkOrderItem;

import java.util.List;

public record WorkOrderResponseDto(
        Integer currentPage,
        Integer endPage,
        List<WorkOrderItem> list
) {

    public static WorkOrderResponseDto of(Integer currentPage, Integer endPage, List<WorkOrderItem> list){
        return new WorkOrderResponseDto(currentPage, endPage, list);
    }

}
