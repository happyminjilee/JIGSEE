package com.sdi.jig.dto.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record JigGraphResponseDto(
        int day,
        float output,
        float cost,
        float yield,
        int countMissingJig
) {
    public static JigGraphResponseDto of(int day,
                                         double outputValue,
                                         double costValue,
                                         double yieldValue,
                                         int countMissingJig
    ) {
        float output = roundToTwoDecimalPlaces(outputValue);
        float cost = roundToTwoDecimalPlaces(costValue);
        float yield = roundToTwoDecimalPlaces(yieldValue);

        return new JigGraphResponseDto(day, output, cost, yield, countMissingJig);
    }

    private static float roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
