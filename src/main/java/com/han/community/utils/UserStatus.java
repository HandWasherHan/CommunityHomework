package com.han.community.utils;

public enum UserStatus {
    NORMAL(0), SUSPENDED(1), DELETED(2);
    private int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
