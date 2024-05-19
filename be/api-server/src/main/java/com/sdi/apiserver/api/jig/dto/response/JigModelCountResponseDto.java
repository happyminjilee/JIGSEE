package com.sdi.jig.dto.response;

import java.util.List;

public record JigModelCountResponseDto(
        int maxCount,
        List<JigModelCount> jigModelCountList
) {
    public static JigModelCountResponseDto from(int maxCount, List<JigModelCount> jigModelCountList){
        return new JigModelCountResponseDto(maxCount, jigModelCountList);
    }

    public record JigModelCount(
            String model,
            int countReady,
            int countWarehouse
    ){
        public static JigModelCount of(String model, int countReady, int countWarehouse){
            return new JigModelCount(model, countReady, countWarehouse);
        }
    }
}
