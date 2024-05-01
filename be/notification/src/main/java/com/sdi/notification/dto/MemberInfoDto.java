package com.sdi.notification.dto;

import com.sdi.notification.util.RoleType;

public record MemberInfoDto(
        String employeeNo,
        RoleType roleType
) {
    public static MemberInfoDto from(MemberResponseDto memberResponseDto) {
        return new MemberInfoDto(memberResponseDto.employeeNo(), RoleType.of(memberResponseDto.role()));
    }
}
