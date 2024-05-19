package com.sdi.notification.dto;

import com.sdi.notification.entity.EmailEntity;

public record MemberEmailDto(
        String employeeNo,
        String email
) {
    public static MemberEmailDto from(EmailEntity emailEntity) {
        return new MemberEmailDto(emailEntity.getEmployeeNo(), emailEntity.getEmail());
    }
}
