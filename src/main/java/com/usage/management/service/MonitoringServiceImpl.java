package com.usage.management.service;

import com.usage.common.dto.SystemPolicyDTO;
import com.usage.common.dto.SystemStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MonitoringServiceImpl implements MonitoringService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public SystemStatusDTO getSystemStatus() {
        return SystemStatusDTO.builder()
            .cacheStatus(getCacheStatus())
            .queueStatus(getQueueStatus())
            .dbStatus(getDbStatus())
            .build();
    }

    private SystemStatusDTO.CacheStatus getCacheStatus() {
        Properties cacheProperties = redisTemplate.getConnectionFactory()
            .getConnection()
            .info("memory");
            
        return SystemStatusDTO.CacheStatus.builder()
            .totalSize(Long.parseLong(cacheProperties.getProperty("total_system_memory", "0")))
            .usedSize(Long.parseLong(cacheProperties.getProperty("used_memory", "0")))
            .hitRate(calculateCacheHitRate())
            .connectionCount(Integer.parseInt(cacheProperties.getProperty("connected_clients", "0")))
            .build();
    }

    private SystemStatusDTO.QueueStatus getQueueStatus() {
        QueueInformation queueInfo = rabbitTemplate.execute(channel -> 
            channel.queueDeclarePassive("usage.queue"));
        QueueInformation deadLetterInfo = rabbitTemplate.execute(channel -> 
            channel.queueDeclarePassive("usage.dead.queue"));
            
        return SystemStatusDTO.QueueStatus.builder()
            .queueSize(queueInfo.getMessageCount())
            .deadLetterSize(deadLetterInfo.getMessageCount())
            .processedCount(getProcessedMessageCount())
            .failedCount(getFailedMessageCount())
            .build();
    }

    private SystemStatusDTO.DbStatus getDbStatus() {
        return SystemStatusDTO.DbStatus.builder()
            .activeConnections(getActiveDbConnections())
            .avgResponseTime(getAvgDbResponseTime())
            .totalQueries(getTotalDbQueries())
            .build();
    }

    @Override
    public SystemPolicyDTO getSystemPolicy() {
        return SystemPolicyDTO.builder()
            .cachePolicy(getCachePolicy())
            .queuePolicy(getQueuePolicy())
            .systemPolicy(getSystemPolicy())
            .build();
    }

    @Override
    public void updateSystemPolicy(SystemPolicyDTO policy) {
        updateCachePolicy(policy.getCachePolicy());
        updateQueuePolicy(policy.getQueuePolicy());
        updateSystemPolicy(policy.getSystemPolicy());
    }

    @Override
    public void logFailedMessage(Object message) {
        // 실패한 메시지 로깅 및 알림 처리
        // 추후 모니터링 시스템 연동 가능
    }

    private double calculateCacheHitRate() {
        Properties stats = redisTemplate.getConnectionFactory()
            .getConnection()
            .info("stats");
        long hits = Long.parseLong(stats.getProperty("keyspace_hits", "0"));
        long misses = Long.parseLong(stats.getProperty("keyspace_misses", "0"));
        return hits == 0 && misses == 0 ? 0 : (double) hits / (hits + misses) * 100;
    }

    private long getProcessedMessageCount() {
        // 메시지 처리 카운트 조회 로직
        return 0;
    }

    private long getFailedMessageCount() {
        // 실패한 메시지 카운트 조회 로직
        return 0;
    }

    private int getActiveDbConnections() {
        // 활성 DB 연결 수 조회 로직
        return 0;
    }

    private double getAvgDbResponseTime() {
        // 평균 DB 응답 시간 조회 로직
        return 0.0;
    }

    private long getTotalDbQueries() {
        // 전체 DB 쿼리 수 조회 로직
        return 0;
    }
}
