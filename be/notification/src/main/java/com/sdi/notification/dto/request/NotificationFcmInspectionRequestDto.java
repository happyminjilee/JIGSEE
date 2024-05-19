package com.sdi.notification.dto.request;

import java.util.List;

public record NotificationFcmInspectionRequestDto(
        String uuid, // 원본 알림 내용 uuid
        List<String> serialNos
) {
}
