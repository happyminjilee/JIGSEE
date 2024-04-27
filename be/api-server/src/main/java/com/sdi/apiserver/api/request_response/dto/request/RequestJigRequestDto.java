package com.sdi.apiserver.api.request_response.dto.request;

import lombok.Value;

import java.util.List;

@Value
public class RequestJigRequestDto {
    List<JigRequest> list;

    public static class JigRequest{
        String to;
        String model;
        Integer count;
    }
}
