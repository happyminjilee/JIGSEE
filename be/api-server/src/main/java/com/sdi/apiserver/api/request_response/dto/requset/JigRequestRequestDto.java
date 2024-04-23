package com.sdi.apiserver.api.request_response.dto.requset;

import lombok.Value;

import java.util.List;

@Value
public class JigRequestRequestDto {
    List<JigRequest> list;

    public static class JigRequest{
        String to;
        String model;
        Integer count;
    }
}
