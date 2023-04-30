package com.han.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.community.entity.MyToken;
import com.han.community.entity.User;
import com.han.community.mapper.MyTokenMapper;
import com.han.community.service.MyTokenService;
import com.han.community.utils.Response;
import org.springframework.stereotype.Service;

@Service
public class MyTokenServiceImpl extends ServiceImpl<MyTokenMapper, MyToken> implements MyTokenService {
    @Override
    public String getUserIdByToken(String token) {
        if (token != null && !token.equals("")) {
            LambdaQueryWrapper<MyToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(MyToken::getContent, token);
            MyToken one = getOne(lambdaQueryWrapper);
            if (one != null) {
                return one.getUserId();
            }

        }
        return null;
    }
}
