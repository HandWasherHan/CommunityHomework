package com.han.community.handler;

import org.apache.ibatis.plugin.Intercepts;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class UserInteceptor implements HandlerInterceptor {
    private static final String ALGORITHM_CLAIM = "alg";
    private static final String ALGORITHM_METHOD = "HS256";
    private static final String USER_NAME = "username";
    private static final String USER_PASSWORD = "password";
    private static final String USER_Id = "id";
    private static final String JWT_SIGN = "root";
    private static final String JWT_HEADER = "Token";

//    private
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = (String)request.getSession().getAttribute(JWT_HEADER);
//        handler.
//        if (token == null) {
//            return false;
//        }

        return true;
    }
}
