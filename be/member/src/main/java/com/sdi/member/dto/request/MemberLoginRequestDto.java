package com.sdi.member.dto.request;

public record MemberLoginRequestDto(
        String employeeNo,
        String password
) {
}
