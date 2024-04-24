package com.sdi.jig.util;

import java.util.List;

public record CheckList(List<CheckItem> list) {

    public static CheckList from(List<CheckItem> list){
        return new CheckList(list);
    }

    public record CheckItem(String content, String standard){
    }
}
