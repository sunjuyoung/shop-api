package com.project.shop.hotproduct.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class ProductViewedCountCache {

    private final StringRedisTemplate redisTemplate;

    //hot-product::product::{productId}::view-count
    private static final String KEY_FORMAT = "hot-product::product::%s::view-count";

    public void createOrUpdate(Long productId, Long viewCount, Duration ttl){
        redisTemplate.opsForValue().set(generateKey(productId), String.valueOf(viewCount), ttl);
    }

    public Long read(Long productId){
        String value = redisTemplate.opsForValue().get(generateKey(productId));
        return value == null ? 0L : Long.parseLong(value);
    }

    private String generateKey(Long productId){
        return String.format(KEY_FORMAT, productId);
    }
}
