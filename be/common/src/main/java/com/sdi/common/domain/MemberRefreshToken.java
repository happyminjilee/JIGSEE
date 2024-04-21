package com.sdi.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "member_refresh_token")
public class MemberRefreshToken {

    @JsonIgnore
    @Id
    @Column(name = "refresh_token_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenSeq;

    @Column(name = "member_employee_no", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String memberEmployeeNo;

    @Column(name = "refresh_token", length = 256)
    @NotNull
    @Size(max = 256)
    private String refreshToken;

    public MemberRefreshToken(
            @NotNull @Size(max = 64) String employeeNo,
            @NotNull @Size(max = 256) String refreshToken
    ) {
        this.memberEmployeeNo = employeeNo;
        this.refreshToken = refreshToken;
    }
}
