package com.sdi.jig.dto.response;

import com.sdi.jig.entity.JigNosqlEntity;
import com.sdi.jig.entity.JigRDBEntity;
import com.sdi.jig.util.CheckList;

import java.util.List;

public record JigResponseDto (
        String model,
        Integer checkType,
        String expectLife,
        List<CheckList> checkList
){

    public static JigResponseDto from(JigRDBEntity rdb, JigNosqlEntity nosql){
        return new JigResponseDto(
                rdb.getModel(),
                rdb.getCheckType(),
                rdb.getExpectLife(),
                nosql.getCheckList()
        );
    }
}
