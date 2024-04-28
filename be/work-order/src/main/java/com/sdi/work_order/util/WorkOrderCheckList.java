package com.sdi.work_order.util;

public record WorkOrderCheckList(
        String uuid,
        String content,
        String standard,
        String measure,
        String memo,
        Boolean passOrNot
) {

    public static WorkOrderCheckList from(String uuid,
                                          String content,
                                          String standard,
                                          String measure,
                                          String memo,
                                          Boolean passOrNot){
        return new WorkOrderCheckList(uuid, content, standard, measure, memo, passOrNot);
    }

    public static WorkOrderCheckList from(String uuid,
                                          String measure,
                                          String memo,
                                          Boolean passOrNot){
        return new WorkOrderCheckList(uuid, null, null, measure, memo, passOrNot);
    }
}
