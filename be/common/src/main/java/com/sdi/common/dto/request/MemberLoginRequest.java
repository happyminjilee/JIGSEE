package com.sdi.common.dto.request;

public record MemberLoginRequest(
        String employeeNo,
        String password
) {
}
