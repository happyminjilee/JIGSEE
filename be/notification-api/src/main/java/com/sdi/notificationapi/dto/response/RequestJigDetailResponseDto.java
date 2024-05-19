package com.sdi.notificationapi.dto.response;

import com.sdi.notificationapi.entity.WantRequestEntity;
import com.sdi.notificationapi.util.JigRequestStatus;

import java.time.LocalDateTime;
import java.util.List;

public record RequestJigDetailResponseDto(
        String id, // 자동생성된 uuid
        JigRequestStatus status, // 요청의 처리 여부
        String from, // 요청 발신자
        String to, // 요청 수신자
        Boolean isAccept, // 승인 or 불출
        LocalDateTime createdAt, // 요청 시간
        LocalDateTime updatedAt, // 요청 처리 시간
        List<String> serialNos // 요청하는 지그 리스트
) {
    public static RequestJigDetailResponseDto from(WantRequestEntity entity) {
        return new RequestJigDetailResponseDto(
                entity.getId(),
                entity.getStatus(),
                entity.getFrom(),
                entity.getTo(),
                entity.getIsAccept(),
                entity.getTime(),
                entity.getUpdateTime(),
                entity.getJigSerialNos());
    }
}
