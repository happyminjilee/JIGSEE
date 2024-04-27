package com.sdi.apiserver.api.jig.dto.request;

public record JigItemExchangeRequestDto(
        String facilitySerialNo,
        String beforeSerialNo,
        String afterSerialNo
) {
}
