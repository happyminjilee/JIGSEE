package com.sdi.jig.dto.response;

import com.sdi.jig.application.wrap.JigInfo;
import com.sdi.jig.util.CheckList;

import java.util.List;

public record JigResponseDto (
        String model,
        Integer checkType,
        String expectLife,
        List<CheckList> checkList
){

    public static JigResponseDto from(JigInfo jigInfo){
        return new JigResponseDto(
                jigInfo.rdb().getModel(),
                jigInfo.rdb().getCheckType(),
                jigInfo.rdb().getExpectLife(),
                jigInfo.nosql().getCheckList()
        );
    }
}
