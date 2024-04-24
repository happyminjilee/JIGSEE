package com.sdi.apiserver.util;

import lombok.Getter;

@Getter
public class CheckDoneList extends CheckList {

    private String uuid;
    private String measure;
    private String memo;
    private Boolean passOrNot;

    public CheckDoneList(String content, String standard, String uuid, String measure, String memo, Boolean passOrNot) {
        super(content, standard);
        this.uuid = uuid;
        this.measure = measure;
        this.memo = memo;
        this.passOrNot = passOrNot;
    }
}
