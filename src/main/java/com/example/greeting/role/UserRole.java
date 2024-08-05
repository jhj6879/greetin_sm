package com.example.greeting.role;

import lombok.Getter;

@Getter
public enum UserRole {

    // 원하는 권한 여기서 입력해서 늘려 가면됨

    ADMIN("ROLE_ADMIN"),
    MMANGER("ROLE_MMANGER"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;

}
