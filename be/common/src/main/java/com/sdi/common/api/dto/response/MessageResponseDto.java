package com.sdi.common.api.dto.response;

import com.sdi.common.api.dto.request.MessageRequestDto;

public record MessageResponseDto(
        String sender, // 발신자
        String receiver, // 수신자
        String type, // 알림 타입
        Long notificationId // 알림 내역 아이디
) {
    public static MessageResponseDto of(MessageRequestDto requestDto, Long notificationId) {
        return new MessageResponseDto(
                requestDto.senderId(),
                requestDto.senderId(),
                requestDto.type(),
                notificationId
        );
    }
}
