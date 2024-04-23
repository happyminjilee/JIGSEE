package com.sdi.apiserver.api.request_response.dto.requset;

import lombok.Value;

import java.util.List;

@Value
public class RequestJigResponseRequestDto {
    Long requestId;
    Boolean isAccept;
    String memo;
    List<String> list; // 지그의 일련번호들
}
