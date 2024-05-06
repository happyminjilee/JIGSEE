package com.sdi.jig.dto.response;

import com.sdi.jig.entity.nosql.JigNosqlEntity;
import com.sdi.jig.entity.rdb.JigRDBEntity;
import com.sdi.jig.util.CheckItem;

import java.util.List;

public record JigResponseDto(
        String model,
        String expectLife,
        List<CheckItem> checkList
) {

    public static JigResponseDto from(JigRDBEntity rdb, JigNosqlEntity nosql) {
        return new JigResponseDto(
                rdb.getModel(),
                rdb.getExpectLife(),
                nosql.getCheckList()
        );
    }
}
