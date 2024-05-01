package com.sdi.notificationapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sdi.notificationapi.entity.RepairRequestEntity;

import java.time.LocalDateTime;
import java.util.List;

public record RepairJigDetailResponseDto(
        String id,
        String from,
        @JsonProperty("createdAt")
        LocalDateTime date,
        String memo,
        List<String> list
) {
    public static RepairJigDetailResponseDto from(RepairRequestEntity entity) {
        return new RepairJigDetailResponseDto(
                entity.getId(),
                entity.getFrom(),
                entity.getTime(),
                entity.getMemo(),
                entity.getSerialNos());
    }
}
