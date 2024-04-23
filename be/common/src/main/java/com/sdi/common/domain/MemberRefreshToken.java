package com.sdi.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class MemberRefreshToken {

    @Id
    private String memberEmployeeNo;

    private String refreshToken;

    public MemberRefreshToken(
            String employeeNo,
            String refreshToken
    ) {
        this.memberEmployeeNo = employeeNo;
        this.refreshToken = refreshToken;
    }
}



