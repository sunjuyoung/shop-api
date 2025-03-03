package com.project.shop.hotproduct.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class ProductCommentCountCache {

    private final StringRedisTemplate redisTemplate;

    //hot-product::product::{productId}::comment-count
    private static final String KEY_FORMAT = "hot-product::product::%s::comment-count";


    //인기글이 선정되기까지만 존재하면 되므로 ttl 설정
    public void createOrUpdate(Long productId, Long commentCount, Duration ttl){
        //data있어도 업데이트
        redisTemplate.opsForValue().set(generateKey(productId), String.valueOf(commentCount), ttl);
    }

    public Long read(Long productId){
        String value = redisTemplate.opsForValue().get(generateKey(productId));
        return value == null ? 0L : Long.parseLong(value);
    }

    private String generateKey(Long productId){
        return String.format(KEY_FORMAT, productId);
    }


}
