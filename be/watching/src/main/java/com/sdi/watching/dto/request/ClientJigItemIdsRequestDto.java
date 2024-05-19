package com.sdi.watching.dto.request;

import java.util.List;

public record ClientJigItemIdsRequestDto(
        List<Long> jigItemIds
) {

    public static ClientJigItemIdsRequestDto from(List<Long> jigItemIds){
        return new ClientJigItemIdsRequestDto(jigItemIds);
    }
}
