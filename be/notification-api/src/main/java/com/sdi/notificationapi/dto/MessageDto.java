package com.sdi.notificationapi.dto;

import com.sdi.notificationapi.util.SseStatus;

public record MessageDto(
        String senderGroup, // 발신자 그룹
        String senderId, // 발신자 사번
        String receiverGroup, // 수신자 그룹
        SseStatus type, // 알림 타입
        String uuid // 알림 세부 정보의 id
) {
    public static MessageDto of(SseStatus type, String senderId, String uuid) {
        switch (type) {
            case REQUEST_JIG: return new MessageDto("ENGINEER", senderId, "MANAGER", type, uuid);
            case RESPONSE_JIG: return new MessageDto("MANAGER", senderId, "ENGINEER", type, uuid);
            case REQUEST_REPAIR: return new MessageDto("PRODUCER", senderId, "ENGINEER", type, uuid);
        }
        return null;
    }
}
