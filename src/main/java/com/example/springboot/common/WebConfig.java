package com.example.springboot.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements  WebMvcConfigurer {

    @Autowired
    JwtInterceptor jwtInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 指定controller统一的接口前缀
        configurer.addPathPrefix("/api", clazz -> clazz.isAnnotationPresent(RestController.class));
    }


    // 加自定义拦截器JwtInterceptor，设置拦截规则
    //addInterceptors（）允许加自定义的拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有以api为开头的请求，然后进入拦截器去验证
        //在拦截请求后，用excludePathPatterns("/api/admin/login")把登录放开，只有登录接口是不需要拦截的，因为登录时是没有token的

        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/admin/login");
    }


}

