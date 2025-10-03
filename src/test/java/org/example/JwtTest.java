package org.example;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("username", "test");
        String jwt = JWT.create()
                .withClaim("user", map) // 添加载荷 payload
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 12)) // 添加过期时间
                .sign(Algorithm.HMAC256("example")); // 指定算法和密钥
        System.out.println(jwt);
    }

    @Test
    public void test1() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6InRlc3QifSwiZXhwIjoxNzU5NDg5Njk0fQ.d_Dc7djbCv1MrwkaH-48PP66R3fMdqoRpGPsBWUDtdI";
        DecodedJWT res = JWT.require(Algorithm.HMAC256("example")).build().verify(token);
        Map<String, Claim> map = res.getClaims();
        System.out.println(map);
    }


}
