package com.telco.query.mapper;

import com.telco.common.dto.UsageDTO;
import com.telco.common.dto.UsageDetail;
import com.telco.query.entity.Usage;
import com.telco.query.entity.BaseUsage;
import org.springframework.stereotype.Component;

@Component
public class UsageMapper {
    
    public UsageDTO toDTO(Usage usage) {
        return UsageDTO.builder()
                .userId(usage.getUserId())
                .voiceUsage(toDetail(usage.getVoiceUsage(), "초"))
                .videoUsage(toDetail(usage.getVideoUsage(), "초"))
                .messageUsage(toDetail(usage.getMessageUsage(), "건"))
                .dataUsage(toDetail(usage.getDataUsage(), "패킷"))
                .build();
    }
    
    private UsageDetail toDetail(BaseUsage usage, String unit) {
        return UsageDetail.builder()
                .totalUsage(usage.getTotalUsage())
                .freeUsage(usage.getFreeUsage())
                .excessUsage(usage.getExcessUsage())
                .unit(unit)
                .build();
    }
}