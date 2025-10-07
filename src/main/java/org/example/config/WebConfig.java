package org.example.config;

import org.example.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     *  - 启动应用后访问 http://localhost:8080/
     *  - 仍可访问示例页面 http://localhost:8080/pages/index.html
     *  - 上传文件后用 http://localhost:8080/files/<文件名> 访问
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns(
                "/user/login",
                "/user/register",
                "/files/*",
                "/pages/*" //
        );
    }
}
