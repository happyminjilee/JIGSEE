package com.sdi.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//사용자
public class Member {

    private Long id;
    private String name;
    private String employeeNo;
    private String password;
    private String role;

}