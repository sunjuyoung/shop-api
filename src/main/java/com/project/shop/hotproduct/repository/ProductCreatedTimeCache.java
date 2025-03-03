package com.project.shop.hotproduct.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Repository
@RequiredArgsConstructor
public class ProductCreatedTimeCache {

    private final StringRedisTemplate redisTemplate;

    //hot-product::product::{productId}::created-time
    private static final String KEY_FORMAT = "hot-product::product::%s::created-time";

    public void createOrUpdate(Long productId, LocalDateTime createdTime, Long ttl){        //createdAt을 문자열로 변환하여 저장
        redisTemplate.opsForValue().set(
                generateKey(productId),
                String.valueOf(createdTime.toInstant(ZoneOffset.UTC).toEpochMilli()),
                ttl
        );
    }

    public void delete(Long articleId) {
        redisTemplate.delete(generateKey(articleId));
    }

    public LocalDateTime read(Long productId){
        String value = redisTemplate.opsForValue().get(generateKey(productId));
        if (value == null) {
            return null;
        }

        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Long.parseLong(value)),
                ZoneOffset.UTC
        );
    }

    private String generateKey(Long productId){
        return String.format(KEY_FORMAT, productId);
    }
}
