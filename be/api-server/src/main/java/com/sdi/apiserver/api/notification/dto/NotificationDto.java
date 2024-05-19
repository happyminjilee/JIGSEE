package com.sdi.apiserver.api.notification.dto;


import com.sdi.apiserver.util.NotificationStatus;

public record NotificationDto(
        Long id,
        boolean checkStatus,
        String sender, // 발신자 사번
        String receiver, // 수신자 사번
        NotificationStatus notificationStatus,
        String contentId
) {
}
