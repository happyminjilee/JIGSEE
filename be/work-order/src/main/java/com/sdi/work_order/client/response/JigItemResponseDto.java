package com.sdi.work_order.client.response;

import java.util.List;

public record JigItemResponseDto(
        String serialNo,
        String model,
        List<JigCheckListItem> checkList
) {

    public record JigCheckListItem(
            String content,
            String standard
    ){}
}
