package com.example.easybuy.config;

import com.example.easybuy.intercept.JwtintercapetAdmin;
import com.example.easybuy.intercept.JwtintercapetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

//拦截器配置文件
@Configuration
public class JwtConfigIntercaper implements WebMvcConfigurer {
    @Resource
    private  JwtintercapetAdmin jwtintercapetAdmin;
    @Resource
    private  JwtintercapetUser jwtintercapetUser;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //管理员请求拦截
        registry.addInterceptor(jwtintercapetAdmin)
                .addPathPatterns(
                        "/user/admin/**",
                        "/product/admin/**",
                        "/News/admin/**",
                        "/UserAddress/admin/**",
                        "/productCategory/admin/**",
                        "/order/admin/**",
                        "/orderDetail/admin/**");
        //用户请求拦截
        registry.addInterceptor(jwtintercapetUser)
                .addPathPatterns(
                        "/user/regist/**",
                        "/product/regist/**",
                        "/News/regist/**",
                        "/UserAddress/regist/**",
                        "/productCategory/regist/**",
                        "/order/regist/**",
                        "/orderDetail/regist/**");
//        registry.addInterceptor(new Jwtintercapet())
//                .addPathPatterns("/**")//拦截路径
//                .excludePathPatterns(
//                        "/user/tourist/*",
//                        "/product/tourist/*",
//                        "/News/tourist/*",
//                        "/UserAddress/tourist/*",
//                        "/productCategory/tourist/*",
//                        "/order/tourist/*",
//                        "/orderDetail/tourist/*",
//                        "***/error");//放行路径
    }
}
