package com.han.community.entity.enums;

public enum UserInfoMessageEnum {
    REPEAT_NAME("用户名已被使用"),
    ALTER_SUCCESS("用户信息已成功修改"),
    UNKNOWN_ERROR("未知错误");

    private String message;

    UserInfoMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
