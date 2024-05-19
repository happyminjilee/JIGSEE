package com.sdi.notification.dto;

import com.sdi.notification.entity.NotificationEntity;
import com.sdi.notification.util.NotificationStatus;

public record NotificationDto(
        Long id,
        boolean checkStatus,
        String sender, // 발신자 사번
        String receiver, // 수신자 사번
        NotificationStatus notificationStatus,
        String contentId
) {
    public static NotificationDto from(NotificationEntity notificationEntity) {
        return new NotificationDto(
                notificationEntity.getId(),
                notificationEntity.isCheckStatus(),
                notificationEntity.getSender(),
                notificationEntity.getReceiver(),
                notificationEntity.getNotificationStatus(),
                notificationEntity.getContentId()
        );
    }
}
