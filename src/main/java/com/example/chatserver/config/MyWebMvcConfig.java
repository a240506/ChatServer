package com.example.chatserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
文件创建于  2022/09/17  17：06
*/
//配置跨域
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(1800)
                // 后端必须添加这个才可以前跨域不丢失cookie ，服务器session才可以使用
                //还必须指定请求域名，不可以用 *
                .allowedOrigins("http://localhost:8080/").allowCredentials(true);
    }
}
