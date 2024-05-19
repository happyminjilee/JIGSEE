package com.sdi.apiserver.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not founded"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Token is expired"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Request is invalid"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server error");

    private final HttpStatus status;
    private final String message;
}
