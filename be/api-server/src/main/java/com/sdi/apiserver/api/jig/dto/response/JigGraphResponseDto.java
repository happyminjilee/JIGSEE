package com.sdi.apiserver.api.jig.dto.response;

public record JigGraphResponseDto(
        int day,
        float output,
        float cost,
        float yield,
        int countMissingJig
) {
}
