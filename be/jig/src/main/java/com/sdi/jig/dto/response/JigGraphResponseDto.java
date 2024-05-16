package com.sdi.jig.dto.response;

public record JigGraphResponseDto(
        int day,
        double output,
        double cost,
        double yield,
        int countMissingJig
) {
    public static JigGraphResponseDto of(int day,
                                         double outputValue,
                                         double costValue,
                                         double yieldValue,
                                         int countMissingJig
    ) {
        double roundedOutputValue = roundedValue(outputValue);
        double roundedCostValue = roundedValue(costValue);
        double roundedYieldValue = roundedValue(yieldValue);

        return new JigGraphResponseDto(day, roundedOutputValue, roundedCostValue, roundedYieldValue, countMissingJig);
    }

    private static double roundedValue(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
