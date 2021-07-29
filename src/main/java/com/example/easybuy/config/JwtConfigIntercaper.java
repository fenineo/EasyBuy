package com.example.easybuy.config;

import com.example.easybuy.intercept.Jwtintercapet;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//拦截器配置文件
@Configuration
public class JwtConfigIntercaper implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Jwtintercapet())
                .addPathPatterns("/**")//拦截路径
                .excludePathPatterns(
                        "/user/tourist/*",
                        "/product/tourist/*",
                        "/News/tourist/*",
                        "/UserAddress/tourist/*",
                        "/productCategory/tourist/*",
                        "/order/tourist/*",
                        "/orderDetail/tourist/*",
                        "***/error");//放行路径
    }
}
