package com.telco.query.service;

import com.telco.common.dto.SystemStatusDTO.CacheStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCacheService<T> implements ICacheService<T> {
    
    private final RedisTemplate<String, T> redisTemplate;
    private final RetryTemplate retryTemplate;
    
    @Value("${spring.redis.ttl:600}")
    private long redisTtl;
    
    @Override
    public Optional<T> get(String key) {
        try {
            return retryTemplate.execute(context -> {
                T value = redisTemplate.opsForValue().get(key);
                if (value != null) {
                    redisTemplate.expire(key, redisTtl, TimeUnit.SECONDS);
                }
                return Optional.ofNullable(value);
            });
        } catch (Exception e) {
            log.error("Failed to get value from cache - key: {}, error: {}", key, e.getMessage());
            return Optional.empty();
        }
    }
    
    @Override
    public void set(String key, T value) {
        try {
            retryTemplate.execute(context -> {
                redisTemplate.opsForValue().set(key, value, redisTtl, TimeUnit.SECONDS);
                return null;
            });
        } catch (Exception e) {
            log.error("Failed to set value to cache - key: {}, error: {}", key, e.getMessage());
        }
    }
    
    @Override
    public void delete(String key) {
        try {
            retryTemplate.execute(context -> {
                redisTemplate.delete(key);
                return null;
            });
        } catch (Exception e) {
            log.error("Failed to delete value from cache - key: {}, error: {}", key, e.getMessage());
        }
    }
    
    @Override
    public CacheStatus getStatus() {
        return CacheStatus.builder()
                .totalSize(redisTemplate.getConnectionFactory().getConnection().serverCommands().dbSize())
                .usedSize(redisTemplate.keys("*").size())
                .hitCount(getHitCount())
                .missCount(getMissCount())
                .build();
    }
    
    private long getHitCount() {
        return Optional.ofNullable(redisTemplate.getConnectionFactory()
                .getConnection()
                .serverCommands()
                .info("stats"))
                .map(stats -> extractNumber(stats, "keyspace_hits"))
                .orElse(0L);
    }
    
    private long getMissCount() {
        return Optional.ofNullable(redisTemplate.getConnectionFactory()
                .getConnection()
                .serverCommands()
                .info("stats"))
                .map(stats -> extractNumber(stats, "keyspace_misses"))
                .orElse(0L);
    }
    
    private long extractNumber(String stats, String key) {
        String[] lines = stats.split("\n");
        for (String line : lines) {
            if (line.startsWith(key)) {
                return Long.parseLong(line.split(":")[1].trim());
            }
        }
        return 0;
    }
}
