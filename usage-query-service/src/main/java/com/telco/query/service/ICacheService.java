package com.telco.query.service;

import com.telco.query.dto.CacheStatus;
import java.util.Optional;

public interface ICacheService<T> {
    Optional<T> get(String key);
    void set(String key, T value);
    void delete(String key);
    CacheStatus getStatus();
}