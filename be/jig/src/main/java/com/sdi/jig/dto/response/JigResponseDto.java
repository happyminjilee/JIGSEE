package com.sdi.jig.dto.response;

import com.sdi.jig.entity.JigNosqlEntity;
import com.sdi.jig.entity.JigRDBEntity;
import com.sdi.jig.util.CheckItem;

import java.util.List;

public record JigResponseDto (
        String model,
        String expectLife,
        List<CheckItem> list
){

    public static JigResponseDto from(JigRDBEntity rdb, JigNosqlEntity nosql){
        return new JigResponseDto(
                rdb.getModel(),
                rdb.getExpectLife(),
                nosql.getCheckList()
        );
    }
}
