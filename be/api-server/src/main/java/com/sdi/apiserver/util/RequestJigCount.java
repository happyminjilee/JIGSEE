package com.sdi.apiserver.util;

public record RequestJigCount(
        String model,
        Integer count
) {

    public static RequestJigCount from(String mode, Integer count){
        return new RequestJigCount(mode, count);
    }
}
