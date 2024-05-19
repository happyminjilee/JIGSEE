package com.sdi.apiserver.api.request_response.dto.response;

import java.util.List;

public record RepairJigListResponseDto(
        Integer currentPage,
        Integer endPage,
        List<RepairJigDetailResponseDto> list
) {
}
