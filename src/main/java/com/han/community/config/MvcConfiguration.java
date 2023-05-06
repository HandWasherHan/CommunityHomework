package com.han.community.config;

import com.han.community.handler.AdminInterceptor;
import com.han.community.handler.LoginInterceptor;
import com.han.community.handler.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class MvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    UserInterceptor userInterceptor;

    @Autowired
    LoginInterceptor loginInterceptor;

    @Autowired
    AdminInterceptor adminInterceptor;

//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations()
//    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
//        userInterceptor.
//        registry.addInterceptor(userInterceptor).addPathPatterns("/user");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/**/ban/**")
                .addPathPatterns("/**/delete/**")
                .addPathPatterns("/user/sign/admin");
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/user")
                .addPathPatterns("/post/add")
                .addPathPatterns("/comment/add/**")
                .addPathPatterns("/user/info/alter")
                .addPathPatterns("/**/add-to-comment/**")
//                .addPathPatterns("use")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/sign_in");

//        registry.
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            // 解决 Controller 返回普通文本中文乱码问题
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }

            // 解决 Controller 返回json对象中文乱码问题
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }
}
