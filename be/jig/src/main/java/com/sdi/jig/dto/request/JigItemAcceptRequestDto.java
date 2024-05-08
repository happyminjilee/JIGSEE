package com.sdi.jig.dto.request;

import java.util.List;

public record JigItemAcceptRequestDto(
        String requestId,
        Boolean isAccept,
        String memo,
        List<String> serialNos
) {
}
