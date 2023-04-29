package com.han.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.han.community.CommonValues;
import com.han.community.entity.User;
import com.han.community.mapper.UserMapper;
import com.han.community.service.UserService;
import com.han.community.utils.CommunityStringUtils;
import com.han.community.utils.Response;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController {
    private static final String SUCCESS_SIGN = "注册成功";
    private static final String FAILURE_SIGN = "注册失败";
    private static final String SUCCESS_LOGIN = "登录成功，欢迎您， ";
    private static final String FAILURE_LOGIN = "登录失败，请检查用户名或密码是否正确";
    private static final String REPEAT_NAME_SIGN = "用户名已存在";
    private static final String ALGORITHM_CLAIM = "alg";
    private static final String ALGORITHM_METHOD = "HS256";
    private static final String USER_NAME = "username";
    private static final String USER_PASSWORD = "password";
    private static final String USER_Id = "id";
    private static final String JWT_SIGN = "root";


    @Autowired
    UserService userService;



    @PostMapping("/sign")
    public String doSign(@RequestBody User user) {
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

        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder.setHeaderParam(ALGORITHM_CLAIM, ALGORITHM_METHOD)
                .claim(USER_Id, user.getId())
                .claim(USER_NAME, user.getUsername())
                .claim(USER_PASSWORD, user.getPassword())
                .signWith(SignatureAlgorithm.HS256, JWT_SIGN)
                .compact();
        Response<String> response = Response.success(SUCCESS_SIGN);
        response.setEntity(jwtToken);
        return response.toJson();


    }

    @PostMapping("/login")
    public String doLogin(@RequestBody User user, @RequestHeader("Token") String token) {
        System.err.println(token);
        String id;
        String username;
        String password;
        String salt;
        if (token != null && !token.equals("")) {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(JWT_SIGN);
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            id = body.get(USER_Id, String.class);
            username = body.get(USER_NAME, String.class);
            password = body.get(USER_PASSWORD, String.class);
            System.err.println(password);

        } else {
            username = user.getUsername();
            password = user.getPassword();
            id = user.getId();
        }

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (id != null) {
            lambdaQueryWrapper.eq(User::getId, id);
        } else {
            lambdaQueryWrapper.eq(User::getUsername, username);
        }
        User one = userService.getOne(lambdaQueryWrapper);
        if (one == null) {
            return Response.fail(FAILURE_LOGIN).toJson();
        }
        if (!password.equals(one.getPassword())) {
            return Response.fail(FAILURE_LOGIN).toJson();
        }
        return Response.success(SUCCESS_LOGIN + username).toJson();
    }

}
