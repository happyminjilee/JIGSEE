package com.sdi.notificationapi.dto;

import com.sdi.notificationapi.util.JigStatus;

public record JigItemStatusUpdateDto(
        String serialNo, // 지그 일련번호
        JigStatus status // 변경할 상태
) {
    public static JigItemStatusUpdateDto ready(String serialNo) {
        return new JigItemStatusUpdateDto(serialNo, JigStatus.READY);
    }
}
