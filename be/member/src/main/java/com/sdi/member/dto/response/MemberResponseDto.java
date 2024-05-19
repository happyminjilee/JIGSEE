package com.sdi.member.dto.response;

import com.sdi.member.entity.MemberEntity;

public record MemberResponseDto(
        String name,
        String employeeNo
) {
    public static MemberResponseDto fromEntity(MemberEntity memberEntity) {
        return new MemberResponseDto(
            memberEntity.getName(),
            memberEntity.getEmployeeNo()
        );
    }
}
