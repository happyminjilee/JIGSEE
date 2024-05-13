package com.sdi.jig.dto.response;

import com.sdi.jig.entity.nosql.JigNosqlEntity;
import com.sdi.jig.util.CheckItem;

import java.util.List;

public record JigUpdatedCheckListResponseDto(
        List<UpdatedJig> updatedJigList
) {
    public static JigUpdatedCheckListResponseDto from (List<UpdatedJig> updatedJigList) {
        return new JigUpdatedCheckListResponseDto(updatedJigList);
    }

    public record UpdatedJig(
            String model,
            List<CheckItem> checkItems
    ) {
        public static UpdatedJig of (JigNosqlEntity entity) {
            return new UpdatedJig(entity.getId(), entity.getCheckList());
        }
    }
}
