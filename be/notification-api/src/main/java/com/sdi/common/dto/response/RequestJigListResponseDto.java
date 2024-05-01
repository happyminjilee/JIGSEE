package com.sdi.common.dto.response;

import java.util.List;

public record RequestJigListResponseDto(
        Integer currentPage,
        Integer endPage,
        List<RequestJigDetailResponseDto> list
) {
    public static RequestJigListResponseDto of(int currentPage, int endPage, List<RequestJigDetailResponseDto> list) {
        return new RequestJigListResponseDto(currentPage, endPage, list);
    }
}
