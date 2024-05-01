package com.sdi.notificationapi.dto;

public record RequestJigDto(
        String jigModel, // 지그 모델
        Integer count // 수량
) {
}