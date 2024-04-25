package com.sdi.member.dto.response;

import com.sdi.member.entity.RoleType;

public record MemberLoginResponseDto(
        Long id,
        String name,
        String employeeNo,
        RoleType role,
        String token
) {
}
