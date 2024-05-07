package com.sdi.notification.entity;

import com.sdi.notification.dto.request.NotificationFcmInspectionRequestDto;
import com.sdi.notification.dto.request.MessageRequestDto;
import com.sdi.notification.util.NotificationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "check_status")
    private boolean checkStatus;
    @Column(name = "sender_id")
    private String sender; // 발신자 사번
    @Column(name = "receiver_id")
    private String receiver; // 수신자 사번
    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;
    @Column(name = "content_id")
    private String contentId;
    public static NotificationEntity of(String sender, String receiver, MessageRequestDto messageRequestDto) {
        return new NotificationEntity(null, false, sender, receiver, messageRequestDto.type(), messageRequestDto.uuid());
    }

    public static NotificationEntity of(String receiver, NotificationFcmInspectionRequestDto notificationFcmInspectionRequestDto) {
        return new NotificationEntity(null, false, "SYSTEM", receiver, NotificationStatus.PERIODIC_INSPECTION, notificationFcmInspectionRequestDto.uuid());
    }
}