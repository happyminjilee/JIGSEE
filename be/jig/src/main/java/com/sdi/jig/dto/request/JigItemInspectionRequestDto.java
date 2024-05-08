package com.sdi.jig.dto.request;


import java.util.List;

public record JigItemInspectionRequestDto(
        List<Long> jigItemIds
) {
}
