package com.sdi.apiserver.api.request_response.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record RepairJigDetailResponseDto(
        String id, // uuid
        String from, // 요청자
        LocalDateTime createdAt, // 요청 시간
        String memo, // 보수 요청 메모
        List<String> list // 지그 일련번호 리스트
) {
}
