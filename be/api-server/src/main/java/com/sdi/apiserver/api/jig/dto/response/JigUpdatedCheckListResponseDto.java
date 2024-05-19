package com.sdi.apiserver.api.jig.dto.response;


import com.sdi.apiserver.util.CheckItem;

import java.util.List;

public record JigUpdatedCheckListResponseDto(
        List<UpdatedJig> updatedJigList
) {
    public record UpdatedJig(
            String model,
            List<CheckItem> checkItems
    ) {}
}
