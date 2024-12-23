package com.telco.management.service.cache;

import com.telco.management.dto.CacheStatus;
import java.util.Optional;

public interface ICacheService<T> {
    Optional<T> get(String key);
    void set(String key, T value);
    void delete(String key);
    CacheStatus getStatus();
}