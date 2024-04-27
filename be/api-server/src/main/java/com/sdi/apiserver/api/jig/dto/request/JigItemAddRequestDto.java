package com.sdi.apiserver.api.jig.dto.request;

import lombok.Value;

import java.util.List;

@Value
public class JigItemAddRequestDto {
    List<JigItemCreate> list;

    public static class JigItemCreate{
        String model;
        List<String> serialNos;
    }
}
