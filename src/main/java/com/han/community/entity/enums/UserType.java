package com.han.community.entity.enums;

public enum UserType {
    COMMON_USER(0);


    private int type;

    UserType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
