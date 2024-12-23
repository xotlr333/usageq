package com.telco.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsageDTO {
    private String userId;
    private UsageDetail voiceUsage;
    private UsageDetail videoUsage;
    private UsageDetail messageUsage;
    private UsageDetail dataUsage;
}