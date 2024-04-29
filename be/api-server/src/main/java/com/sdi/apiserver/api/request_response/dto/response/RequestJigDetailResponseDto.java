package com.sdi.apiserver.api.request_response.dto.response;

import com.sdi.apiserver.util.RequestJigCount;

import java.time.LocalDateTime;
import java.util.List;

public record RequestJigDetailResponseDto(
        Boolean isManager,
        Long id,
        String status,
        String from,
        String to,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<RequestJigCount> list
) {
    public static RequestJigDetailResponseDto from(Boolean isManager,
                                                   Long id,
                                                   String status,
                                                   String from,
                                                   String to,
                                                   String memo,
                                                   LocalDateTime createdAt,
                                                   LocalDateTime updatedAt,
                                                   List<RequestJigCount> list){
        return new RequestJigDetailResponseDto(isManager, id, status, from, to, memo, createdAt, updatedAt, list);
    }
}
