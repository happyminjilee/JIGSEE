package com.sdi.common.dto;

import com.sdi.common.util.JigStatus;

public record JigItemStatusUpdateDto(
        String serialNo, // 지그 일련번호
        JigStatus status // 변경할 상태
) {
    public static JigItemStatusUpdateDto ready(String serialNo) {
        return new JigItemStatusUpdateDto(serialNo, JigStatus.READY);
    }
}
