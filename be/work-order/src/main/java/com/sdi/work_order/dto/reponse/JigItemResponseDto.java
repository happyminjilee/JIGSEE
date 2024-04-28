package com.sdi.work_order.dto.reponse;

import java.util.List;

public record JigItemResponseDto(
        String serialNo,
        String model,
        List<JigCheckListItem> jigCheckListItem
) {

    public record JigCheckListItem(
            String content,
            String standard
    ){}
}
