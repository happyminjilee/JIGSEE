package com.sdi.jig.dto.response;

import java.util.List;

public record JigOptimalIntervalResponseDto(
        List<Double> data
) {
    public static JigOptimalIntervalResponseDto of(List<Double> data) {
        return new JigOptimalIntervalResponseDto(data);
    }
}
