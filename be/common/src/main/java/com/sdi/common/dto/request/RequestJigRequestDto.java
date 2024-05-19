package com.sdi.common.dto.request;

import com.sdi.common.dto.RequestJigDto;

import java.util.List;

/**
 *
 * @param sender 토큰에서 가져오거나 Member API 호출하든지 해야함(수정 필요)
 * @param list
 * @param memo
 */
public record RequestJigRequestDto(
        String sender, // 요청자 사번
        List<RequestJigDto> list, // 요청 지그 리스트
        String memo // 메모
) {
}
