package com.sdi.common.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sdi.common.util.SseStatus;

public record MessageRequestDto(
        String senderGroup, // 발신자 그룹
        String senderId, // 발신자
        String receiverGroup, // 수신자 그룹
        SseStatus type, // 알림 타입
        String uuid // 알림 세부 정보의 id
) {
    public MessageRequestDto(@JsonProperty("senderGroup") String senderGroup,
                             @JsonProperty("senderId") String senderId,
                             @JsonProperty("receiverGroup") String receiverGroup,
                             @JsonProperty("type") SseStatus type,
                             @JsonProperty("uuid") String uuid) {
                this.senderGroup = senderGroup.replaceAll("^ROLE_","");
                this.senderId = senderId;
                this.receiverGroup = receiverGroup.replaceAll("^ROLE_","");
                this.type = type;
                this.uuid = uuid;
        }
}
