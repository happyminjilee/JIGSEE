package com.sdi.jig.dto.request;

public record JigItemExchangeRequestDto(
        String facilitySerialNo,
        String beforeSerialNo,
        String afterSerialNo
) {
}
