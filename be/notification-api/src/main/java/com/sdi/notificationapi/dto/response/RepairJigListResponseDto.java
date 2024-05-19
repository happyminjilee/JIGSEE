package com.sdi.notificationapi.dto.response;

import java.util.List;

public record RepairJigListResponseDto(
        Integer currentPage,
        Integer endPage,
        List<RepairJigDetailResponseDto> list
) {
    public static RepairJigListResponseDto of(Integer currentPage, Integer endPage, List<RepairJigDetailResponseDto> list) {
        return new RepairJigListResponseDto(currentPage, endPage, list);
    }
}
