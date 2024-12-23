package com.usage.query.repository;

import com.usage.query.entity.UsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsageRepository extends JpaRepository<UsageEntity, Long> {
    Optional<UsageEntity> findByUserId(String userId);
}
