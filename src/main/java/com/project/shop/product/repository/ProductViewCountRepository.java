package com.project.shop.product.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductViewCountRepository {


    private final StringRedisTemplate redisTemplate;
    //view::product::{id}::view_count
    private static final String KEY_FORMAT = "view::product::%d::view_count";


    public Long read(Long productId){
        String key = gnerateKey(productId);
        String value = redisTemplate.opsForValue().get(key);
        if(value == null){
            return 0L;
        }
        return Long.parseLong(value);
    }

    public Long increase(Long productId){
        String key = gnerateKey(productId);
        return redisTemplate.opsForValue().increment(key);
    }


    private String gnerateKey(Long productId){
        return String.format(KEY_FORMAT, productId);
    }

}
