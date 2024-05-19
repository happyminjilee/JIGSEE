package com.sdi.apiserver.api.notification.dto.response;

import com.sdi.apiserver.api.notification.dto.NotificationDto;

import java.util.List;

public record NotificationListResponseDto(
        int currentPage,
        int endPage,
        List<NotificationDto> notifications
) {
}
