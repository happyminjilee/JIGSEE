package com.sdi.work_order.util;

import java.util.List;
import java.util.UUID;

public record WorkOrderCheckItem(
        String uuid,
        String content,
        String standard,
        String measure,
        String memo,
        Boolean passOrNot
) {

    public static List<WorkOrderCheckItem> from(List<JigCheckItem> jigCheckItem) {
        if(jigCheckItem == null){
            return List.of();
        }

        return jigCheckItem.stream()
                .map(e -> new WorkOrderCheckItem(
                        UUID.randomUUID().toString(),
                        e.content(),
                        e.standard(),
                        "",
                        "",
                        false
                )).toList();
    }
}
