package com.org.umsc.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {
    USER("ROLE_USER", "사용자 권한"),
    ADMIN("ROLE_ADMIN", "관리자 권한"),
    NONE("NONE", "권한 없음");

    private final String code;
    private final String value;

    public static RoleType of(String code){
        return Arrays.stream(RoleType.values()).filter(roleType -> roleType.getCode().equals(code)).findAny().orElse(NONE);
    }
}
