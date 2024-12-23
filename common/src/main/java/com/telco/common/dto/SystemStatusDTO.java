package com.telco.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "시스템 상태 정보")
@Getter
@Builder
public class SystemStatusDTO {
    @Schema(description = "캐시 상태")
    private CacheStatus cacheStatus;
    
    @Schema(description = "큐 상태")
    private QueueStatus queueStatus;
    
    @Schema(description = "DB 상태")
    private DBStatus dbStatus;
}

@Getter
@Builder
class CacheStatus {
    private long totalSize;
    private long usedSize;
    private long hitCount;
    private long missCount;
}

@Getter
@Builder
class QueueStatus {
    private long queueSize;
    private long deadLetterQueueSize;
    private long processedCount;
    private long failureCount;
}

@Getter
@Builder
class DBStatus {
    private long connectionCount;
    private long activeQueries;
    private String status;
}
