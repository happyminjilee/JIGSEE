package com.sdi.apiserver.api.request_response.dto.request;

import java.util.List;

/**
 *
 * @param requestId
 * @param isAccept
 * @param memo
 * @param serialNos
 */
public record ResponseJigRequestDto(
        String requestId, // 원본 요청 uuid
        Boolean isAccept, // 불출 여부
        String memo, // 반려 사유
        List<String> serialNos // 불출 지그 일련번호 목록
) {
}
