package com.sdi.apiserver.api.request_response.response;

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
    public static class JigRequest{
        Long requestId;
        String from;
        String to;
        String model;
        Integer count;
        LocalDateTime requestedAt;
    }
}
