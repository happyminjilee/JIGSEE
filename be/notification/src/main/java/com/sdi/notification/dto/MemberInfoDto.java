package com.sdi.notification.dto;

import com.sdi.notification.util.RoleType;

public record MemberInfoDto(
        String employeeNo,
        RoleType roleType
) {
}
