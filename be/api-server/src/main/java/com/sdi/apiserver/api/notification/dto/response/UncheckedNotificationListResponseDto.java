package com.sdi.apiserver.api.notification.dto.response;


import com.sdi.apiserver.api.notification.dto.NotificationDto;

import java.util.List;

public record UncheckedNotificationListResponseDto(
        List<NotificationDto> notifications
) {
}
