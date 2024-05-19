package com.sdi.work_order.client.response;

import com.sdi.work_order.util.JigCheckItem;

import java.util.List;


public record JigItemResponseDto(
        String id,
        String model,
        String serialNo,
        String status,
        String expectLife,
        Integer useCount,
        String useAccumulationTime,
        Integer repairCount,
        List<JigCheckItem> checkList
) {

}
