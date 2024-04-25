package com.sdi.common.api.dto;

import com.sdi.common.util.RoleType;

public record MemberInfoDto(
        String employeeNo,
        RoleType roleType
) {
}
