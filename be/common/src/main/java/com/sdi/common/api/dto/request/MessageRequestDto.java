package com.sdi.common.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sdi.common.util.SseStatus;

public record MessageRequestDto(
        String senderGroup, // 발신자 그룹
        String senderId, // 발신자
        String receiverGroup, // 수신자 그룹
        String receiverId, // 수신자
        SseStatus type, // 알림 타입
        String uuid // 알림 세부 정보의 id
) {
    public MessageRequestDto(@JsonProperty("sender_group") String senderGroup,
                             @JsonProperty("sender_id") String senderId,
                             @JsonProperty("receiver_group") String receiverGroup,
                             @JsonProperty("receiver_id") String receiverId,
                             @JsonProperty("type") SseStatus type,
                             @JsonProperty("uuid") String uuid) {
                this.senderGroup = senderGroup.replaceAll("^ROLE_","");
                this.senderId = senderId;
                this.receiverGroup = receiverGroup.replaceAll("^ROLE_","");
                this.receiverId = receiverId;
                this.type = type;
                this.uuid = uuid;
        }
}
