package com.han.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.han.community.CommonValues;
import com.han.community.entity.MyToken;
import com.han.community.service.MyTokenService;
import com.han.community.utils.UserValue;
import com.han.community.entity.User;
import com.han.community.service.UserService;
import com.han.community.utils.CommunityStringUtils;
import com.han.community.utils.Response;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;


@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController implements UserValue {

    @Autowired
    UserService userService;

    @Autowired
    MyTokenService myTokenService;

    @PostMapping("/sign")
    public String doSign(@RequestBody User user, boolean rememberMe) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        System.err.println(user.getUsername());
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User one = userService.getOne(lambdaQueryWrapper);
        if (one != null) {
            return Response.fail(REPEAT_NAME_SIGN).toJson();
        }
        String salt = CommunityStringUtils.generateUUID(CommonValues.DEFAULT_SALT_LENGTH);

//        System.err.println(salt);
        String password = CommunityStringUtils.md5Digest(
                CommunityStringUtils.md5Digest(user.getPassword()) + salt);
//        System.err.println("password" + password);
        user.setSalt(salt);
        user.setPassword(password);
        userService.save(user);
//        OAuth2ResourceServerProperties.Jwt

        // TODO 将token存到redis里，以实现分布式一致性、并发幂等性，目前仅存入mysql中
        String jwtToken = userService.loginService(user, rememberMe);

        Response<String> response = Response.success(SUCCESS_SIGN);
        response.setEntity(jwtToken);
        return response.toJson();


    }

    @PostMapping("/login")
    public String doLogin(@RequestBody User user, @RequestHeader("Token") String token, HttpSession httpSession, boolean rememberMe) {
        System.err.println(token);
        String id;
        String username;
        String password;
        String salt;
        if (token != null && !token.equals("")) {
            LambdaQueryWrapper<MyToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(MyToken::getContent, token);
            MyToken one = myTokenService.getOne(lambdaQueryWrapper);
            if (one != null) {
                LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
                userLambdaQueryWrapper.eq(User::getId, one.getUserId());
                User one1 = userService.getOne(userLambdaQueryWrapper);
                username = one1.getUsername();
                return Response.success(SUCCESS_LOGIN + username).toJson();
            }

        }
        username = user.getUsername();
        password = user.getPassword();
        id = user.getId();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (id != null) {
            lambdaQueryWrapper.eq(User::getId, id);
        } else {
            lambdaQueryWrapper.eq(User::getUsername, username);
        }
        User one = userService.getOne(lambdaQueryWrapper);
        if (one == null || !password.equals(one.getPassword())) {
            return Response.fail(FAILURE_LOGIN).toJson();
        }
        String jwtToken = userService.loginService(user, rememberMe);
        return Response.success(SUCCESS_LOGIN + username).toJson();
    }

}
