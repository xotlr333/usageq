package com.telco.query.service;

import com.telco.common.dto.UsageDTO;

public interface IUsageQueryService {
    UsageDTO getUserUsage(String userId);
}
