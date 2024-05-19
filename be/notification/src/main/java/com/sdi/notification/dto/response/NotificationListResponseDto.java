package com.sdi.notification.dto.response;

import com.sdi.notification.dto.NotificationDto;

import java.util.List;

public record NotificationListResponseDto(
        int currentPage,
        int endPage,
        List<NotificationDto> notifications
) {
    public static NotificationListResponseDto of(int currentPage, int endPage, List<NotificationDto> notifications) {
        return new NotificationListResponseDto(currentPage, endPage, notifications);
    }
}
