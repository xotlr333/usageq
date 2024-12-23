package com.telco.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "시스템 정책 설정")
@Getter
@Setter
public class SystemPolicyDTO {
    private CachePolicy cachePolicy;
    private QueuePolicy queuePolicy;
    private SystemPolicy systemPolicy;
}

@Getter
@Setter
class CachePolicy {
    private int ttlMinutes;
    private long maxSize;
}

@Getter
@Setter
class QueuePolicy {
    private int maxRetryCount;
    private int retryIntervalSeconds;
}

@Getter
@Setter
class SystemPolicy {
    private boolean autoScalingEnabled;
    private boolean traceEnabled;
}
