package com.sdi.apiserver.api.request_response.dto.requset;

import lombok.Value;

@Value
public class JigRequestRequestDto {
    String to;
    String model;
    Integer count;
}
