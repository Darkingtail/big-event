package org.example.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.utils.JwtUtil;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${token}")
    private String tokenHeaderName;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证令牌
        String token = request.getHeader(tokenHeaderName);
        try {

            if (null == stringRedisTemplate.opsForValue().get(token)) {
                throw new RuntimeException("redis token is null");
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            System.out.println("Jwt parse =======> " + claims);
            ThreadLocalUtil.set(claims);
            return true;

        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 删除 ThreadLocal中的数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
