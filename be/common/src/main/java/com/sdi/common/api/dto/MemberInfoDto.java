package com.sdi.common.api.dto;

import com.sdi.common.util.RoleType;

public record MemberInfoDto(
        String employeeNo,
        RoleType roleType
) {
    public static MemberInfoDto from(MemberResponseDto memberResponseDto) {
        return new MemberInfoDto(memberResponseDto.employeeNo(), RoleType.of(memberResponseDto.role()));
    }
}
