package com.han.community.utils;

public interface UserValue {
    public static final String SUCCESS_SIGN = "注册成功";
    public static final String SUCCESS_LOGIN = "登录成功，欢迎您， ";
    public static final String FAILURE_SIGN = "注册失败";
    public static final String FAILURE_LOGIN = "登录失败，请检查用户名或密码是否正确";
    public static final String REPEAT_NAME_SIGN = "用户名已存在";
    public static final String ALGORITHM_CLAIM = "alg";
    public static final String ALGORITHM_METHOD = "HS256";
    public static final String USER_NAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_Id = "id";
    public static final String JWT_SIGN = "root";
    public static final String JWT_HEADER = "Token";
    public static final String USER_NAME_INFO = "usernameMsg";
    public static final String USER_PASSWORD_INFO = "passwordMsg";
    public static final String BLANK_INFO = "账号或密码为空";
    public static final String UUID = "UUID";
    public static final long DEFAULT_EXPIRE_TIME = 1000 * 60 * 30;
    public static final long DEFAULT_ALIVE_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;
}
