package com.sdi.common.dto.request;

import java.util.List;

/**
 *
 * @param sender 토큰에서 가져오거나 Member API 호출하든지 해야함(수정 필요)
 * @param requestId
 * @param isAccept
 * @param memo
 * @param serialNos
 */
public record ResponseJigRequestDto(
        String sender, // 응답자 사번
        String requestId, // 원본 요청 uuid
        Boolean isAccept, // 불출 여부
        String memo, // 반려 사유
        List<String> serialNos // 불출 지그 일련번호 목록
) {
}
