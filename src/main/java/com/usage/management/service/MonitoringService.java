package com.usage.management.service;

import com.usage.common.dto.SystemPolicyDTO;
import com.usage.common.dto.SystemStatusDTO;

public interface MonitoringService {
    SystemStatusDTO getSystemStatus();
    SystemPolicyDTO getSystemPolicy();
    void updateSystemPolicy(SystemPolicyDTO policy);
    void logFailedMessage(Object message);
}
