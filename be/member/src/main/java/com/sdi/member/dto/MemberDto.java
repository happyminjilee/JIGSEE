package com.sdi.member.dto;

import com.sdi.member.entity.MemberEntity;
import com.sdi.member.entity.RoleType;

public record MemberDto(
        Long id,
        String name,
        String employeeNo,
        String password,
        RoleType role
) {
    public static MemberDto fromEntity(MemberEntity entity) {
        return new MemberDto(
                entity.getId(),
                entity.getName(),
                entity.getEmployeeNo(),
                entity.getPassword(),
                entity.getRole()
        );
    }
}
