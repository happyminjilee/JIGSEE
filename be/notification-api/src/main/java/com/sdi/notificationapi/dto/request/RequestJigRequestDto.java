package com.sdi.notificationapi.dto.request;

import java.util.List;

/**
 *
 * @param serialNos 요청 지그 일련번호 리스트
 */
public record RequestJigRequestDto(
        List<String> serialNos // 요청 지그 리스트
) {
}
