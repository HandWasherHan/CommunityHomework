package com.han.community.handler;

import com.han.community.entity.User;
import com.han.community.utils.HostHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    HostHandler hostHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (request.get)
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        User user = new User();
        user.setId("999");
        hostHandler.add(user);
        return true;
    }
}
