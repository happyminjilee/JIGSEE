package com.sdi.apiserver.api.jig.dto.request;

import lombok.Value;

import java.util.List;

@Value
public class JigItemCreateRequestDto {
    List<JigItemCreate> list;

    public static class JigItemCreate{
        String serialNo;
        Integer count;
    }
}
