package com.usage.common.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class UsageUpdateDTO {
    private String userId;
    private String type;  // VOICE, VIDEO, MESSAGE, DATA
    private long amount;
    private LocalDateTime timestamp;
}
