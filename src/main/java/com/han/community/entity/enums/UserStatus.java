package com.han.community.entity.enums;

public enum UserStatus {
    NORMAL(0), SUSPENDED(1), DELETED(2), ADMIN(3);
    private int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
