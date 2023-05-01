package com.han.community.handler;

import com.han.community.annotations.LoginRequired;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Intercepts;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

@Slf4j
@Component
//@Intercepts({"/**"})
public class UserInterceptor implements HandlerInterceptor {
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
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Annotation methodAnnotation = handlerMethod.getMethodAnnotation(LoginRequired.class);
//        return methodAnnotation
        return false;
    }

}
