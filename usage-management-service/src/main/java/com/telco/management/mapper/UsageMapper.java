package com.telco.management.mapper;

import com.telco.common.dto.UsageDTO;
import com.telco.common.dto.UsageDetail;
import com.telco.management.entity.*;
import org.springframework.stereotype.Component;

@Component
public class UsageMapper {

    public UsageDTO toDTO(Usage usage) {
        if (usage == null) return null;

        return UsageDTO.builder()
                .userId(usage.getUserId())
                .voiceUsage(createUsageDetail(
                        usage.getVoiceUsage().getTotalUsage(),
                        usage.getVoiceUsage().getFreeUsage(),
                        usage.getVoiceUsage().getExcessUsage(),
                        "초"))
                .videoUsage(createUsageDetail(
                        usage.getVideoUsage().getTotalUsage(),
                        usage.getVideoUsage().getFreeUsage(),
                        usage.getVideoUsage().getExcessUsage(),
                        "초"))
                .messageUsage(createUsageDetail(
                        usage.getMessageUsage().getTotalUsage(),
                        usage.getMessageUsage().getFreeUsage(),
                        usage.getMessageUsage().getExcessUsage(),
                        "건"))
                .dataUsage(createUsageDetail(
                        usage.getDataUsage().getTotalUsage(),
                        usage.getDataUsage().getFreeUsage(),
                        usage.getDataUsage().getExcessUsage(),
                        "패킷"))
                .build();
    }

    private UsageDetail createUsageDetail(long totalUsage, long freeUsage, long excessUsage, String unit) {
        return UsageDetail.builder()
                .totalUsage(totalUsage)
                .freeUsage(freeUsage)
                .excessUsage(excessUsage)
                .unit(unit)
                .build();
    }
}