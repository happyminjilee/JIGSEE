package com.sdi.jig.dto.response;

import com.sdi.jig.entity.JigNosqlEntity;
import com.sdi.jig.entity.JigRDBEntity;
import com.sdi.jig.util.CheckList;

public record JigResponseDto (
        String model,
        String expectLife,
        CheckList checkList
){

    public static JigResponseDto from(JigRDBEntity rdb, JigNosqlEntity nosql){
        return new JigResponseDto(
                rdb.getModel(),
                rdb.getExpectLife(),
                CheckList.from(nosql.getCheckList())
        );
    }
}
