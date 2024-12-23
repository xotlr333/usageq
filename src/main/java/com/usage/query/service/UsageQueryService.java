package com.usage.query.service;

import com.usage.common.dto.UsageDTO;

public interface UsageQueryService {
    UsageDTO getUserUsage(String userId);
}
