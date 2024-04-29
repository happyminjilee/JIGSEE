package com.sdi.work_order.util;

import com.sdi.work_order.client.response.JigItemResponseDto.JigCheckListItem;

import java.util.List;
import java.util.UUID;

public record WorkOrderCheckList(
        String uuid,
        String content,
        String standard,
        String measure,
        String memo,
        Boolean passOrNot
) {

    public static List<WorkOrderCheckList> from(List<JigCheckListItem> jigCheckListItem) {
        if(jigCheckListItem == null){
            return List.of();
        }

        return jigCheckListItem.stream()
                .map(e -> new WorkOrderCheckList(
                        UUID.randomUUID().toString(),
                        e.content(),
                        e.standard(),
                        "",
                        "",
                        false
                )).toList();
    }
}
