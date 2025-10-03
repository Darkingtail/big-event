package org.example.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${token}")
    private String tokenHeaderName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证令牌
        String token = request.getHeader(tokenHeaderName);
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            return true;

        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}
