package com.sdi.apiserver.api.request_response.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record RepairJigListResponseDto(
        Integer currentPage,
        Integer endPage,
        List<RequestJigRepair> list
) {
    public static RepairJigListResponseDto from(Integer currentPage,
                                                Integer endPage,
                                                List<RequestJigRepair> list){
        return new RepairJigListResponseDto(currentPage, endPage, list);
    }

    public record RequestJigRepair(
            Long id,
            String from,
            LocalDateTime createdAt
    ) {
        public static RequestJigRepair from(Long id,
                                                                   String from,
                                                                   LocalDateTime createdAt){
            return new RequestJigRepair(id, from, createdAt);
        }
    }
}
