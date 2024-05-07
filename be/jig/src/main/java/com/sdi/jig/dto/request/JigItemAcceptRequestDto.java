package com.sdi.jig.dto.request;

import java.util.List;

public record JigItemAcceptRequestDto(
        List<String> serialNos
) {
}
