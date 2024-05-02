package com.sdi.notification.dto.response;

import com.sdi.notification.dto.NotificationDto;

import java.util.List;

public record UncheckedNotificationListResponseDto(
        List<NotificationDto> notifications
) {
    public static UncheckedNotificationListResponseDto from(List<NotificationDto> notifications) {
        return new UncheckedNotificationListResponseDto(notifications);
    }
}
