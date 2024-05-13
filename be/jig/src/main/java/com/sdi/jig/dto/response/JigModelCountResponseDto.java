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
            int count
    ){
        public static JigModelCount of(String model, int count){
            return new JigModelCount(model, count);
        }
    }
}
