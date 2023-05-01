package com.han.community.config;

import com.han.community.handler.LoginInterceptor;
import com.han.community.handler.UserInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Properties;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    UserInterceptor userInterceptor;

    @Autowired
    LoginInterceptor loginInterceptor;

//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations()
//    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
//        userInterceptor.
//        registry.addInterceptor(userInterceptor).addPathPatterns("/user");
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/login");
//        registry.
    }
}
