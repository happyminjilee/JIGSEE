package com.sdi.common.dto.response;

public record MemberLoginResponse(
        Long id,
        String name,
        String employeeNo,
        String role,
        String token
) {
}
