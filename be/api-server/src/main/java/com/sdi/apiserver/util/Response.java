package com.sdi.apiserver.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Response<T> {
    private final String resultCode;
    private final T result;

    @JsonCreator
    public Response(@JsonProperty("resultCode") String resultCode,
                    @JsonProperty("result") T result) {
        this.resultCode = resultCode;
        this.result = result;
    }

    public static Response<Void> success() {
        return new Response<Void>("SUCCESS", null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }

    public static Response<Void> error(String errorCode) {
        return new Response<>(errorCode, null);
    }
}
