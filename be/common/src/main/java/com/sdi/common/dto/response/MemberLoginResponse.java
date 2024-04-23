package com.sdi.common.dto.response;

import com.sdi.common.domain.RoleType;

public record MemberLoginResponse(
        Long id,
        String name,
        String employeeNo,
        RoleType role,
        String token
) {
}
