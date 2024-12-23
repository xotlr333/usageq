package com.usage.management.service;

import com.usage.common.dto.SystemPolicyDTO;
import com.usage.common.dto.SystemStatusDTO;
import com.usage.common.util.UnitConverter;
import com.usage.management.dto.UsageUpdateDTO;
import com.usage.management.repository.UsageManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsageManagementServiceImpl implements UsageManagementService {
    private final UsageManagementRepository usageRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MonitoringService monitoringService;

    @Value("${usage.queue.retry-count}")
    private int retryCount;

    @Value("${usage.queue.retry-interval}")
    private int retryInterval;

    @Override
    @Transactional
    public void updateUsage(UsageUpdateDTO usageData) {
        try {
            convertUnits(usageData);
            rabbitTemplate.convertAndSend("usage.exchange", "usage.update", usageData);
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("usage.dlx", "usage.dead", usageData);
            throw new RuntimeException("사용량 업데이트 실패", e);
        }
    }

    private void convertUnits(UsageUpdateDTO usageData) {
        switch (usageData.getType().toUpperCase()) {
            case "VOICE", "VIDEO" -> 
                usageData.setAmount(UnitConverter.convertToSeconds(usageData.getAmount(), "SECONDS"));
            case "DATA" -> 
                usageData.setAmount(UnitConverter.convertToPackets(usageData.getAmount(), "PACKETS"));
        }
    }

    @Override
    public void processDeadLetterQueue() {
        int currentRetry = 0;
        while (currentRetry < retryCount) {
            Object message = rabbitTemplate.receiveAndConvert("usage.dead");
            if (message == null) break;

            try {
                UsageUpdateDTO usageData = (UsageUpdateDTO) message;
                updateUsage(usageData);
                Thread.sleep(retryInterval);
            } catch (Exception e) {
                currentRetry++;
                if (currentRetry >= retryCount) {
                    monitoringService.logFailedMessage(message);
                }
            }
        }
    }

    @Override
    public SystemStatusDTO getSystemStatus() {
        return monitoringService.getSystemStatus();
    }

    @Override
    public SystemPolicyDTO getSystemPolicy() {
        return monitoringService.getSystemPolicy();
    }

    @Override
    public void updateSystemPolicy(SystemPolicyDTO policy) {
        monitoringService.updateSystemPolicy(policy);
    }
}
