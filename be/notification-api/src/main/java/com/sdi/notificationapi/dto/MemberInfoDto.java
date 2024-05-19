package com.sdi.notificationapi.dto;

import com.sdi.notificationapi.util.RoleType;

public record MemberInfoDto(
        String id,
        String name,
        String employeeNo,
        RoleType roleType
) {
}
