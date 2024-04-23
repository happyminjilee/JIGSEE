package com.sdi.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {
    MANAGER("ROLE_MANAGER", "관리자"),
    ENGINEER("ROLE_ENGINEER", "기술자"),
    PRODUCER("ROLE_PRODUCER", "생산자");

    private final String code;
    private final String displayName;

    public static RoleType of(String code) {
        return Arrays.stream(RoleType.values())
                .filter(roleType -> roleType.getCode().equals(code))
                .findAny()
                .orElse(PRODUCER);
    }
}
