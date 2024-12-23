package com.usage.query.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usage_data")
@Getter
@Setter
public class UsageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    private long voiceTotalUsage;
    private long voiceFreeUsage;
    private long videoTotalUsage;
    private long videoFreeUsage;
    private long messageTotalUsage;
    private long messageFreeUsage;
    private long dataTotalUsage;
    private long dataFreeUsage;
}
