package com.han.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.community.CommonValues;
import com.han.community.entity.MyToken;
import com.han.community.entity.User;
import com.han.community.mapper.UserMapper;
import com.han.community.service.MyTokenService;
import com.han.community.service.UserService;
import com.han.community.utils.CommunityStringUtils;
import com.han.community.utils.UserValue;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserValue {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MyTokenService myTokenService;

    @Override
    public String loginService(User user,  boolean rememberMe) {
//        System.err.println(user.getId());
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder.setHeaderParam(ALGORITHM_CLAIM, ALGORITHM_METHOD)
                .claim(USER_Id, user.getId())
                .claim(USER_NAME, user.getUsername())
                .claim(UUID, CommunityStringUtils.generateUUID(CommonValues.DEFAULT_UUID_LENGTH))
                .signWith(SignatureAlgorithm.HS256, JWT_SIGN)
                .compact();
        MyToken myToken = new MyToken();
        myToken.setUserId(user.getId());
        myToken.setContent(jwtToken);
        Date date = new Date(System.currentTimeMillis() +
                (rememberMe ? DEFAULT_ALIVE_EXPIRE_TIME : DEFAULT_EXPIRE_TIME));
        myToken.setExpired(date);
        myTokenService.save(myToken);
        return jwtToken;
    }

    @Override
    public User getUserById(String id) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId, id);
        return getOne(lambdaQueryWrapper);

    }

    @Override
    public User getUserByName(String name) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        System.err.println(user.getUsername());
        lambdaQueryWrapper.eq(User::getUsername, name);
        return getOne(lambdaQueryWrapper);
    }
}
