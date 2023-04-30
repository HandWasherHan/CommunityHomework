package com.han.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.community.entity.User;

import java.util.Map;

public interface UserService extends IService<User> {


    String loginService(User user,  boolean rememberMe);
}
