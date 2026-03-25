package com.gestoracademico.gestoracademico.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private final StringRedisTemplate redisTemplate;
    private static final String REDIS_PREFIX = "blacklist:token:";
    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void banToken(String token, long ttlMilis) {
        redisTemplate.opsForValue()
            .set(REDIS_PREFIX + token , "BANNED", ttlMilis, TimeUnit.MILLISECONDS);
    }

    public boolean isBanned(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(REDIS_PREFIX + token));
    }
}
