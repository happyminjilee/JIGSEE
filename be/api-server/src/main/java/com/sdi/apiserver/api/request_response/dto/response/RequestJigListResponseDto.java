package com.sdi.apiserver.api.request_response.dto.response;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class RequestJigListResponseDto {

    Boolean isManager;
    Integer currentPage;
    Integer endPage;
    List<JigRequest> list;

    @Value
    public static class JigRequest {
        Long id;
        String status;
        String from;
        String to;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }
}
