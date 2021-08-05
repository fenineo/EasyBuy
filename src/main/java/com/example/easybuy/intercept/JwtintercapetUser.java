package com.example.easybuy.intercept;

import com.example.easybuy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * 普通用户请求拦截器
 */
@Component
public class JwtintercapetUser implements HandlerInterceptor {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (redisTemplate.hasKey(token)){
            User user = (User) redisTemplate.opsForValue().get(token);                  //获取用户对象
            long time = redisTemplate.opsForValue().getOperations().getExpire(token);   //获取key剩余过期时间
            if(time < 300){
                redisTemplate.opsForValue().set(token,user, Duration.ofMinutes(30L));        //过期时间小于5分钟时重新设置过期时间
            }
            return true;
        }
        return false;
    }
}
