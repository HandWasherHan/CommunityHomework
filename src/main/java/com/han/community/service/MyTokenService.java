package com.han.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.community.entity.MyToken;
import com.han.community.entity.User;

public interface MyTokenService extends IService<MyToken> {

    String getUserIdByToken(String token);
}
