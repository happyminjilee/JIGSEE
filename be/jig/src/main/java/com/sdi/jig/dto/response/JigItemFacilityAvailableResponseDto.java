package com.sdi.jig.dto.response;

import java.util.List;

public record JigItemFacilityAvailableResponseDto(
        List<String> list
) {
    public static JigItemFacilityAvailableResponseDto from(List<String> list) {
        return new JigItemFacilityAvailableResponseDto(list);
    }
}
