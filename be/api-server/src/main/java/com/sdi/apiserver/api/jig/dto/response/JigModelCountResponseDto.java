package com.sdi.apiserver.api.jig.dto.response;

import java.util.List;

public record JigModelCountResponseDto(
        int maxCount,
        List<JigModelCount> jigModelCountList
) {
    public record JigModelCount(
            String model,
            int count
    ){

    }
}
