package com.telco.query.service;

import com.telco.query.dto.CacheStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCacheService<T> implements ICacheService<T> {

    private final RedisTemplate<String, T> redisTemplate;

    @Value("${spring.redis.ttl:600}")
    private long redisTtl;

    @Override
    public Optional<T> get(String key) {
        try {
            T value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                redisTemplate.expire(key, redisTtl, TimeUnit.SECONDS);
            }
            return Optional.ofNullable(value);
        } catch (Exception e) {
            log.error("Failed to get value from cache - key: {}, error: {}", key, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void set(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, value, redisTtl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Failed to set value to cache - key: {}, error: {}", key, e.getMessage());
        }
    }

    @Override
    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Failed to delete value from cache - key: {}, error: {}", key, e.getMessage());
        }
    }

    @Override
    public CacheStatus getStatus() {
        try {
            Set<String> keys = redisTemplate.keys("*");
            long keyCount = keys != null ? keys.size() : 0;

            return CacheStatus.builder()
                    .totalSize(keyCount)  // 전체 크기를 keys 수로 대체
                    .usedSize(keyCount)   // 사용중인 크기도 동일하게 설정
                    .hitCount(0L)
                    .missCount(0L)
                    .build();
        } catch (Exception e) {
            log.error("Failed to get cache status: {}", e.getMessage());
            return CacheStatus.builder().build();
        }
    }
}