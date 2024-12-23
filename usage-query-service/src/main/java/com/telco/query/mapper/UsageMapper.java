package com.telco.query.mapper;

import com.telco.common.dto.UsageDTO;
import com.telco.common.dto.UsageDetail;
import com.telco.query.entity.*;
import org.springframework.stereotype.Component;

@Component
public class UsageMapper {

    public UsageDTO toDTO(Usage usage) {
        if (usage == null) return null;

        return UsageDTO.builder()
                .userId(usage.getUserId())
                .voiceUsage(createUsageDetail(usage.getVoiceUsage(), "초"))
                .videoUsage(createUsageDetail(usage.getVideoUsage(), "초"))
                .messageUsage(createUsageDetail(usage.getMessageUsage(), "건"))
                .dataUsage(createUsageDetail(usage.getDataUsage(), "패킷"))
                .build();
    }

    private UsageDetail createUsageDetail(BaseUsage usage, String unit) {
        if (usage == null) return null;
        return UsageDetail.builder()
                .totalUsage(usage.getTotalUsage())
                .freeUsage(usage.getFreeUsage())
                .excessUsage(usage.getExcessUsage())
                .unit(unit)
                .build();
    }
}