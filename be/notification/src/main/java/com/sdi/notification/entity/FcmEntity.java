package com.sdi.notification.entity;

import com.sdi.notification.dto.request.FcmTokenRequestDto;
import com.sdi.notification.util.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "fcm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "employee_no")
    private String employeeNo;
    private String token;

    public static FcmEntity from(String employeeNo, FcmTokenRequestDto fcmTokenRequestDto) {
        return new FcmEntity(null, employeeNo, fcmTokenRequestDto.token());
    }
}
