package com.sdi.apiserver.api.jig.dto.request;

public record JigItemDeleteAndRepairRequestDto(
        String serialNo,
        Boolean isAllPass
) {
}
