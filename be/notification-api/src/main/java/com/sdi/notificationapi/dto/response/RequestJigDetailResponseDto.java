package com.sdi.notificationapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sdi.notificationapi.dto.RequestJigDto;
import com.sdi.notificationapi.entity.WantRequestEntity;
import com.sdi.notificationapi.util.JigRequestStatus;

import java.time.LocalDateTime;
import java.util.List;

public record RequestJigDetailResponseDto(
        String id, // 자동생성된 uuid
        @JsonProperty("createAt")
        LocalDateTime time, // 요청 시간
        List<RequestJigDto> jigRequestDtoList, // 요청하는 지그 리스트
        String memo, // 메모
        Boolean isAccept, // 승인 여부
        JigRequestStatus status, // 처리 여부
        @JsonProperty("updateAt")
        LocalDateTime updateTime, // 처리 시간
        String to // 처리자
) {
    public static RequestJigDetailResponseDto from(WantRequestEntity entity) {
        return new RequestJigDetailResponseDto(
                entity.getId(),
                entity.getTime(),
                entity.getJigRequestDtoList(),
                entity.getMemo(),
                entity.getIsAccept(),
                entity.getStatus(),
                entity.getUpdateTime(),
                entity.getTo());
    }
}
