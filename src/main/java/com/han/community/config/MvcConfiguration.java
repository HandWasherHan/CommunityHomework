package com.han.community.config;

import com.han.community.handler.AdminInterceptor;
import com.han.community.handler.LoginInterceptor;
import com.han.community.handler.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping ("/**")
                //设置允许跨域请求的域名
                .allowedOriginPatterns ("*")
                //是否允许证书
                .allowCredentials (true)
                //设置允许的方法
                .allowedMethods ("GET","POST")
                //设置允许的header属性
                .allowedHeaders ("*")
                //允许跨域时间
                .maxAge (3600);
    }

    /**
     * 静态资源映射，只要继承了WebMvcConfigurationSupport并且有@Configuration注解的mvc配置类，都必须重新设置映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/")
                .addResourceLocations("classpath:/static/html/");
    }

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
