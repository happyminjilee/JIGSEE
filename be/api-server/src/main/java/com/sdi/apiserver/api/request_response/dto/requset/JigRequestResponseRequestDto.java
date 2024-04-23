package com.sdi.apiserver.api.request_response.dto.requset;

import lombok.Value;

@Value
public class JigRequestResponseRequestDto {
    Long requestId;
    Boolean isAccept;
    String memo;
}
