package com.usage.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SystemStatusDTO {
    private CacheStatus cacheStatus;
    private QueueStatus queueStatus;
    private DbStatus dbStatus;
    
    @Getter
    @Builder
    public static class CacheStatus {
        private long totalSize;
        private long usedSize;
        private double hitRate;
        private int connectionCount;
    }
    
    @Getter
    @Builder
    public static class QueueStatus {
        private long queueSize;
        private long deadLetterSize;
        private long processedCount;
        private long failedCount;
    }
    
    @Getter
    @Builder
    public static class DbStatus {
        private int activeConnections;
        private double avgResponseTime;
        private long totalQueries;
    }
}
