package com.sdi.apiserver.api.jig.dto.request;

public record JigItemUpdateStatusRequestDto(
        String serialNo,
        String status
) {
}
