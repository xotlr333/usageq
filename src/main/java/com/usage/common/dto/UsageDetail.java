package com.usage.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsageDetail {
    private long totalUsage;
    private long freeUsage;
    private long excessUsage;
    private String unit;
}
