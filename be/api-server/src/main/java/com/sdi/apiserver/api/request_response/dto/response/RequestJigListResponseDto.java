package com.sdi.apiserver.api.request_response.dto.response;

import java.util.List;

public record RequestJigListResponseDto(
        Integer currentPage,
        Integer endPage,
        List<RequestJigDetailResponseDto> list
) {
}
