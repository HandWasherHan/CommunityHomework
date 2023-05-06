package com.han.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void fun() {
        redisTemplate.opsForValue().set("name", "han");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }

}
