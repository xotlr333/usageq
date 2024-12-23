package com.usage.management.listener;

import com.usage.management.dto.UsageUpdateDTO;
import com.usage.management.service.UsageManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsageQueueListener {
    private final UsageManagementService usageManagementService;

    @RabbitListener(queues = "usage.queue")
    public void handleUsageUpdate(UsageUpdateDTO usageData) {
        usageManagementService.updateUsage(usageData);
    }
}
