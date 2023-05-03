package com.han.community.entity.enums;

public enum LoginMessage {
    SUCCESS("登录成功，欢迎您："),
    WRONG_INFO_FAILURE("用户名或密码错误"),
    SUSPENDED_USER("用户账号被停用"),
    DELETED("用户已注销"),
    UNAUTHORIZED("未授权，请检查是否登录"),
    ADMIN_SUCCESS("欢迎您，管理员： ");

    private String message;

    LoginMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
