package com.sdi.common.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "member")
//사용자
public class MemberEntity {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name="employee_no")
    private String employeeNo;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleType role;

}