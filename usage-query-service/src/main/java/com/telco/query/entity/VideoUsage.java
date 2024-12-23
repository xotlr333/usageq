package com.telco.query.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoUsage extends BaseUsage {

    @Builder
    public VideoUsage(long totalUsage, long freeUsage) {
        super(totalUsage, freeUsage);
    }
}