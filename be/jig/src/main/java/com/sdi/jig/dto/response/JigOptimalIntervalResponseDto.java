package com.sdi.jig.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record JigOptimalIntervalResponseDto(
        List<BigDecimal> data
) {
    public static JigOptimalIntervalResponseDto of(List<BigDecimal> data) {
        return new JigOptimalIntervalResponseDto(data);
    }
}
