package com.sdi.common.dto;

import com.sdi.common.domain.MemberEntity;
import com.sdi.common.domain.RoleType;

public record Member(
        Long id,
        String name,
        String employeeNo,
        String password,
        RoleType role
) {
    public static Member fromEntity(MemberEntity entity) {
        return new Member(
                entity.getId(),
                entity.getName(),
                entity.getEmployeeNo(),
                entity.getPassword(),
                entity.getRole()
        );
    }
}
