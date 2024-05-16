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
        double roundedOutputValue = Math.round(outputValue * 100.0) / 100.0;
        double roundedCostValue = Math.round(costValue * 100.0) / 100.0;
        double roundedYieldValue = Math.round(yieldValue * 100.0) / 100.0;

        return new JigGraphResponseDto(day, roundedOutputValue, roundedCostValue, roundedYieldValue, countMissingJig);
    }
}
