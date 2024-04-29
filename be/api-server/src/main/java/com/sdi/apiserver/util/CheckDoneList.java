package com.sdi.apiserver.util;


public record CheckDoneList(
        String content,
        String standard,
        String uuid,
        String measure,
        String memo,
        Boolean passOrNot
) {

    public CheckDoneList(String content, String standard, String uuid, String measure, String memo, Boolean passOrNot) {
        this.content = content;
        this.standard = standard;
        this.uuid = uuid;
        this.measure = measure;
        this.memo = memo;
        this.passOrNot = passOrNot;
    }
}
