package com.sdi.common.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MessageRequestDto(
        String senderGroup, // 발신자 그룹
        String senderId, // 발신자
        String receiverGroup, // 수신자 그룹
        String receiverId, // 수신자
        String type // 알림 타입
) {
        public MessageRequestDto(@JsonProperty("sender_group")
                                 String senderGroup, @JsonProperty("sender_id")
                                 String senderId, @JsonProperty("receiver_group")
                                 String receiverGroup, @JsonProperty("receiver_id")
                                 String receiverId, String type) {
                this.senderGroup = senderGroup.replaceAll("^ROLE_","");
                this.senderId = senderId;
                this.receiverGroup = receiverGroup.replaceAll("^ROLE_","");
                this.receiverId = receiverId;
                this.type = type;
        }
}
