package com.sdi.apiserver.api.member.dto.request;

import lombok.Value;

@Value
public class LoginRequestDto {
    String employeeNo;
    String password;
}
