package com.sdi.jig.dto.request;

import com.sdi.jig.util.JigStatus;

public record JigItemUpdateStatusRequestDto(
        String serialNo,
        JigStatus status
) {
}
