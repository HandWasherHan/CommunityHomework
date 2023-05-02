package com.han.community.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.han.community.entity.User;
import com.han.community.service.UserService;
import com.han.community.utils.HostHandler;
import com.han.community.utils.UserValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor, UserValue {

    @Autowired
    HostHandler hostHandler;

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (request.get)
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        String id = (String)request.getSession().getAttribute(USER_Id);
        if (id == null) {
            return false;
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId, id);
        User one = userService.getOne(lambdaQueryWrapper);
        hostHandler.add(one);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHandler.remove();
    }
}
