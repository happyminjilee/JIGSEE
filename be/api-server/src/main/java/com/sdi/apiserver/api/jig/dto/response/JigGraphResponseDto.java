package com.sdi.apiserver.api.jig.dto.response;

public record JigGraphResponseDto(
        int day,
        double output,
        double cost,
        double yield,
        int countMissingJig
) {
}
