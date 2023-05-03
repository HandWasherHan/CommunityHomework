package com.han.community.handler;

import com.han.community.entity.User;
import com.han.community.entity.enums.UserStatus;
import com.han.community.service.UserService;
import com.han.community.utils.HostHandler;
import com.han.community.utils.UserValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor, UserValue {

    @Autowired
    UserService userService;

    @Autowired
    HostHandler hostHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = (String) request.getSession().getAttribute(USER_Id);
        User userById = userService.getUserById(userId);
        if (userById.getStatus() != UserStatus.ADMIN.getCode()) {
            return false;
        }
        hostHandler.add(userById);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHandler.remove();
    }
}
