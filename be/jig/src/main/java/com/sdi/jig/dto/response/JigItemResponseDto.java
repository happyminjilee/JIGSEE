package com.sdi.jig.dto.response;

import com.sdi.jig.entity.JigItemRDBEntity;
import com.sdi.jig.entity.JigNosqlEntity;
import com.sdi.jig.util.CheckItem;
import com.sdi.jig.util.JigStatus;

import java.util.List;

public record JigItemResponseDto(
        Long id,
        String model,
        String serialNo,
        JigStatus status,
        String expectLife,
        Integer useCount,
        String useAccumulationTime,
        Integer repairCount,
        List<CheckItem> checkList
) {

    public static JigItemResponseDto from(JigItemRDBEntity rdb,
                                          JigNosqlEntity nosql,
                                          Integer useCount,
                                          String useAccumulationTime,
                                          Integer repairCount) {
        return new JigItemResponseDto(
                rdb.getId(),
                nosql.getId(), // nosql에서 id가 model임
                rdb.getSerialNo(),
                rdb.getStatus(),
                rdb.getJig().getExpectLife(),
                useCount,
                useAccumulationTime,
                repairCount,
                nosql.getCheckList()
        );
    }
}
