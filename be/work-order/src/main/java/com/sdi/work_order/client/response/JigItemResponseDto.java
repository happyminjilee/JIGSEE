package com.sdi.work_order.client.response;

import com.sdi.work_order.util.JigCheckItem;

import java.util.List;


public record JigItemResponseDto(
        String model,
        String serialNo,
        List<JigCheckItem> checkList
) {

}
