package com.sdi.common.dto;

import com.sdi.common.domain.MemberEntity;

public record Member(
        Long id,
        String name,
        String employeeNo,
        String password,
        String role
) {
    public static Member of(Long id, String name, String employeeNo, String password, String role) {
        return new Member(
                id,
                name,
                employeeNo,
                password,
                role
        );
    }

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
