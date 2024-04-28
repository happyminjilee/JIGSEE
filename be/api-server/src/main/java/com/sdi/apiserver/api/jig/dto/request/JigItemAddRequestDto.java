package com.sdi.apiserver.api.jig.dto.request;

import java.util.List;


public record JigItemAddRequestDto(
        List<JigAddRequest> list
) {

    public record JigAddRequest(
            String model,
            List<String> serialNos
    ) {
    }
}

