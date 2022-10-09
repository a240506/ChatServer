package com.example.chatserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

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

    //加这个才可以 开启 WebSocket
    /** 扫描注解了@ServerEndpoint注解的类 */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    //静态文件夹开放
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //解决springboot上传图片后无法立即访问需重启的问题,不可以使用classpath:/static/ 会无法直接访问
        registry.addResourceHandler("/static/**").addResourceLocations("file:D:\\迅雷下载\\my-chat项目图片文件夹\\");
    }
}
