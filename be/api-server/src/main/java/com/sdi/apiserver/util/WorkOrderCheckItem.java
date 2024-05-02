package com.sdi.apiserver.util;


public record WorkOrderCheckItem(
        String uuid,
        String content,
        String standard,
        String measure,
        String memo,
        Boolean passOrNot
) {
}
