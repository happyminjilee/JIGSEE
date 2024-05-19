package com.sdi.jig.dto.request;

public record JigItemDeleteAndRepairRequestDto(
        String serialNo,
        Boolean isAllPass
) {
}
