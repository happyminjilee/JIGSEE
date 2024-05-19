package com.sdi.notificationapi.dto.request;

import java.util.List;

/**
 *
 * @param serialNos
 * @param memo
 */
public record RepairJigRequestDto(
        List<String> serialNos, // 지그 일련번호 리스트
        String memo // 메모
) {
}
