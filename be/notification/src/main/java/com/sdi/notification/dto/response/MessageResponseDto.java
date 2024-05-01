package com.sdi.notification.dto.response;

import com.sdi.notification.dto.request.MessageRequestDto;
import com.sdi.notification.util.SseStatus;

public record MessageResponseDto(
        String sender, // 발신자
        String receiver, // 수신자
        SseStatus type, // 알림 타입
        Long notificationId // 알림 내역 아이디
) {
    public static MessageResponseDto of(MessageRequestDto requestDto, Long notificationId, String receiverId) {
        return new MessageResponseDto(
                requestDto.senderId(),
                receiverId,
                requestDto.type(),
                notificationId
        );
    }
}
