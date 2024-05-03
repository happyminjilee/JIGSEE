package com.sdi.apiserver.api.request_response.dto.response;

import com.sdi.apiserver.util.JigRequestStatus;

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
}
