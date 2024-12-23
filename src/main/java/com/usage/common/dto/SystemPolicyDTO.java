package com.usage.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SystemPolicyDTO {
    private CachePolicy cachePolicy;
    private QueuePolicy queuePolicy;
    private SystemPolicy systemPolicy;
    
    @Getter
    @Builder
    public static class CachePolicy {
        private int ttlSeconds;
        private long maxSize;
        private boolean enableCompression;
    }
    
    @Getter
    @Builder
    public static class QueuePolicy {
        private int retryCount;
        private int retryIntervalMs;
        private int deadLetterTtl;
    }
    
    @Getter
    @Builder
    public static class SystemPolicy {
        private boolean enableAutoScaling;
        private boolean enableTracing;
        private int alertThreshold;
    }
}
