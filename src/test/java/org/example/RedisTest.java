package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

// 这个注解添加之后，在运行单元测试方法方法之前，会先初始化Spring容器
@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() {
        stringRedisTemplate.opsForValue().set("key1", "value1", 15, TimeUnit.SECONDS);
    }

    @Test
    public void test1() {
        String key1 = stringRedisTemplate.opsForValue().get("key1");
        System.out.println("key1=====>" + key1);
    }
}
