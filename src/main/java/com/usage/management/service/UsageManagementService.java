package com.usage.management.service;

import com.usage.common.dto.SystemPolicyDTO;
import com.usage.common.dto.SystemStatusDTO;
import com.usage.management.dto.UsageUpdateDTO;

public interface UsageManagementService {
    void updateUsage(UsageUpdateDTO usageData);
    void processDeadLetterQueue();
    SystemStatusDTO getSystemStatus();
    SystemPolicyDTO getSystemPolicy();
    void updateSystemPolicy(SystemPolicyDTO policy);
}
