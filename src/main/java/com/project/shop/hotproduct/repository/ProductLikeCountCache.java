package com.project.shop.hotproduct.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class ProductLikeCountCache {

    private final StringRedisTemplate redisTemplate;

    //hot-product::product::{productId}::like-count
    private static final String KEY_FORMAT = "hot-product::product::%s::like-count";

    public void createOrUpdate(Long productId, Long likeCount, Duration ttl){
        redisTemplate.opsForValue().set(generateKey(productId), String.valueOf(likeCount), ttl);
    }

    public Long read(Long productId){
        String value = redisTemplate.opsForValue().get(generateKey(productId));
        return value == null ? 0L : Long.parseLong(value);
    }

    private String generateKey(Long productId){
        return String.format(KEY_FORMAT, productId);
    }
}
