package com.telco.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsageUpdateRequest {
    private String userId;
    private String type;
    private long amount;
    private int retryCount;
    
    public void incrementRetryCount() {
        this.retryCount++;
    }
}
