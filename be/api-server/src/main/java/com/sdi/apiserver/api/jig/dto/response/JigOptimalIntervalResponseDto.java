package com.sdi.apiserver.api.jig.dto.response;

import java.util.List;

public record JigOptimalIntervalResponseDto(
        List<Double> data
) {
}
