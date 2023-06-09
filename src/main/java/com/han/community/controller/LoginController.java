package com.han.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.han.community.entity.enums.LoginMessage;
import com.han.community.entity.enums.UserStatus;
import com.han.community.service.MyTokenService;
import com.han.community.utils.*;
import com.han.community.entity.User;
import com.han.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController implements UserValue {

    @Autowired
    UserService userService;

    @Autowired
    MyTokenService myTokenService;

    @Autowired
    HostHandler hostHandler;

    @Autowired
    MyMailSender mailSender;

    @PostMapping("/try")
    public String tryLogin(String email, String password, HttpSession httpSession, HttpServletRequest request) {
        User user = new User();
        user.setUsername("han");
        user.setPassword(password);
        return doLogin(user, httpSession, request, false);
    }

    @PostMapping("/login")
    public String doLogin(@RequestBody User user, HttpSession httpSession, HttpServletRequest request,
                          @PathVariable(required = false) boolean rememberMe) {
        String id;
        String username;
        String password;
        String token = request.getHeader("Authorization");
        if (token != null) {
            token = token.substring(7);
            id = myTokenService.getUserIdByToken(token);
            if (id == null) {
                return Response.success(FAILURE_LOGIN).toJson();
            }
            User one = userService.getUserById(id);
            if (one == null) {
                return Response.success(FAILURE_LOGIN).toJson();
            }
            username = one.getUsername();
            log.info(USER_LOGIN_WITH_TOKEN + token);
            httpSession.setAttribute(USER_NAME, user.getUsername());
            httpSession.setAttribute(USER_Id, one.getId());
            return Response.success(SUCCESS_LOGIN + username).toJson();
        }
        username = user.getUsername();
        password = user.getPassword();
        id = user.getId();
        User one;
        if (id != null) {
            one = userService.getUserById(user.getId());
        } else {
            one = userService.getUserByName(user.getUsername());
        }
        if (one == null) {
            return Response.fail(FAILURE_LOGIN).toJson();
        }
//        log.info("password: " + password + " after digested: " + CommunityStringUtils.md5Digest(password));
        password = CommunityStringUtils.md5Digest(
                CommunityStringUtils.md5Digest(password)
                        + (one.getSalt() == null ? "" : one.getSalt()));// 兼容旧版本无salt的登录
        if (!password.equals(one.getPassword())) {
            return Response.fail(FAILURE_LOGIN).toJson();
        }
        String jwtToken = userService.loginService(one, rememberMe);
        httpSession.setAttribute("token", jwtToken);
        httpSession.setAttribute(USER_NAME, user.getUsername());
        httpSession.setAttribute(USER_Id, one.getId());
        Response<String> response;
        int status = one.getStatus();
        if (status == UserStatus.DELETED.getCode()) {
            response = Response.fail(403, LoginMessage.DELETED.getMessage());
            return response.toJson();
        }
        if (status == UserStatus.ADMIN.getCode()) {
            response = Response.success(LoginMessage.ADMIN_SUCCESS.getMessage() + username);
        } else {
            response = Response.success(SUCCESS_LOGIN + username);
        }

        response.setEntity(jwtToken);
        return response.toJson();
    }

    @GetMapping(value = "/logout")
    public String doLogOut(HttpSession httpSession) {
        String username = (String)httpSession.getAttribute(USER_NAME);
        if (username == null || username.equals("")) {
            return Response.fail(UNKNOWN_FAILURE).toJson();
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        User one = userService.getOne(lambdaQueryWrapper);
        myTokenService.removeById(one.getId());
        httpSession.removeAttribute(USER_NAME);
        httpSession.removeAttribute(USER_Id);
        return Response.success(LOG_OUT_SUCCESS).toJson();
    }

}
