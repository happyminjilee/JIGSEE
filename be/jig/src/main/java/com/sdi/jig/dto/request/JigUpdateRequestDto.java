package com.sdi.jig.dto.request;

import com.sdi.jig.util.CheckList;

import java.util.List;

import static com.sdi.jig.util.CheckList.*;

public record JigUpdateRequestDto(
        String model,
        List<CheckItem> checkList
) {
}
