package com.sdi.notificationapi.dto.request;

import java.util.List;

/**
 *
 * @param sender 토큰에서 가져오거나 Member API 호출하든지 해야함(수정 필요)
 * @param serialNos
 * @param memo
 */
public record RepairJigRequestDto(
        String sender, // 발신자
        List<String> serialNos, // 지그 일련번호 리스트
        String memo // 메모
) {
}
