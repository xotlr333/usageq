package com.telco.query.service;

import com.telco.common.dto.UsageDTO;
import com.telco.common.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsageQueryServiceImpl implements IUsageQueryService {
    
    private final ICacheService<UsageDTO> cacheService;
    private final UsageDBService usageDBService;
    
    @Override
    public UsageDTO getUserUsage(String userId) {
        // 1. 캐시에서 조회 시도
        Optional<UsageDTO> cachedUsage = cacheService.get(formatCacheKey(userId));
        
        if (cachedUsage.isPresent()) {
            log.info("Cache Hit - userId: {}", userId);
            return cachedUsage.get();
        }
        
        log.info("Cache Miss - userId: {}", userId);
        
        // 2. Cache Miss인 경우 DB에서 조회
        UsageDTO usage = usageDBService.findByUserId(userId);
                
        try {
            // 3. 조회된 데이터를 캐시에 저장
            cacheService.set(formatCacheKey(userId), usage);
            log.info("Cache Update - userId: {}", userId);
        } catch (Exception e) {
            log.error("Failed to update cache - userId: {}, error: {}", userId, e.getMessage());
        }
        
        return usage;
    }
    
    private String formatCacheKey(String userId) {
        return String.format("usage:%s", userId);
    }
}
