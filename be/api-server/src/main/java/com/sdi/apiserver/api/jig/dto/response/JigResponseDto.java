package com.sdi.apiserver.api.jig.dto.response;

import com.sdi.apiserver.util.CheckItem;

import java.util.List;

public record JigResponseDto(
        String model,
        String expectLife,

        List<CheckItem> list
) {
}
