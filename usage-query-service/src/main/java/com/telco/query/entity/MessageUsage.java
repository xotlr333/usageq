package com.telco.query.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageUsage extends BaseUsage {
    @Builder
    public MessageUsage(long totalUsage, long freeUsage) {
        super(totalUsage, freeUsage);
    }
}