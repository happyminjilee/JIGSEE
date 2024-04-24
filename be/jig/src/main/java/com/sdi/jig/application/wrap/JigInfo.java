package com.sdi.jig.application.wrap;

import com.sdi.jig.entity.JigNosqlEntity;
import com.sdi.jig.entity.JigRDBEntity;

public record JigInfo (
        JigRDBEntity rdb,
        JigNosqlEntity nosql
){
    public static JigInfo from(JigRDBEntity rdb, JigNosqlEntity nosql){
        return new JigInfo(rdb, nosql);
    }
}
