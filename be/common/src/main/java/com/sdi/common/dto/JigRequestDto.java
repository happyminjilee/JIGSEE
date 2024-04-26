package com.sdi.common.dto;

public record JigRequestDto(
        String model, // 지그 모델명
        Integer count, // 갯수
        String memo // 메모
) {
}
