package com.sdi.jig.dto.response;

import java.util.List;

public record JigOptimalIntervalResponseDto(
        List<Float> data
) {
    public static JigOptimalIntervalResponseDto of(List<Float> data) {
        return new JigOptimalIntervalResponseDto(data);
    }
}
