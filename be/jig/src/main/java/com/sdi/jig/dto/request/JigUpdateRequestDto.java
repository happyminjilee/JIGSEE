package com.sdi.jig.dto.request;

import com.sdi.jig.util.CheckItem;

import java.util.List;

public record JigUpdateRequestDto(
        String model,
        List<CheckItem> checkList
) {
}
