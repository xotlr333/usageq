package com.usage.query.service;

import com.usage.common.dto.UsageDTO;
import com.usage.common.dto.UsageDetail;
import com.usage.query.entity.UsageEntity;
import com.usage.query.repository.UsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsageQueryServiceImpl implements UsageQueryService {
    private final UsageRepository usageRepository;

    @Cacheable(value = "usage", key = "#userId")
    @Override
    public UsageDTO getUserUsage(String userId) {
        UsageEntity usage = usageRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return UsageDTO.builder()
            .userId(usage.getUserId())
            .voiceUsage(buildUsageDetail(usage.getVoiceTotalUsage(), usage.getVoiceFreeUsage(), "초"))
            .videoUsage(buildUsageDetail(usage.getVideoTotalUsage(), usage.getVideoFreeUsage(), "초"))
            .messageUsage(buildUsageDetail(usage.getMessageTotalUsage(), usage.getMessageFreeUsage(), "건"))
            .dataUsage(buildUsageDetail(usage.getDataTotalUsage(), usage.getDataFreeUsage(), "패킷"))
            .build();
    }

    private UsageDetail buildUsageDetail(long totalUsage, long freeUsage, String unit) {
        long excessUsage = Math.max(0, totalUsage - freeUsage);
        return UsageDetail.builder()
            .totalUsage(totalUsage)
            .freeUsage(freeUsage)
            .excessUsage(excessUsage)
            .unit(unit)
            .build();
    }
}
