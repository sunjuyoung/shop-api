package com.project.shop.product.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//@Service
public class ProductViewCountService {

//    private final RedisTemplate<String,String> redisTemplate;
//    private final static long PREVENT_DUPLICATION_TIME = 3600;
//    private static final String VIEW_COUNT_PREFIX = "view_count:";
//
//    public ProductViewCountService(RedisTemplate<String, String> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    public boolean incrementViewCount(Long productId) {
//        String key = "product:view:" + productId;
//        Boolean keyWasAbsent = redisTemplate.opsForValue().setIfAbsent(key,
//                "viewd",
//                PREVENT_DUPLICATION_TIME, TimeUnit.SECONDS);
//        return keyWasAbsent != null && keyWasAbsent;
//    }
//
//    @Async
//    public void incrementViewCountAsync(Long productId) {
//        String key = VIEW_COUNT_PREFIX + productId;
//        redisTemplate.opsForValue().increment(key);
//    }
//
//    public Map<Long, Long> getAndResetViewCounts() {
//        Set<String> keys = redisTemplate.keys(VIEW_COUNT_PREFIX + "*");
//        if (keys == null || keys.isEmpty()) {
//            return Collections.emptyMap();
//        }
//
//        Map<Long, Long> viewCounts = new HashMap<>();
//        for (String key : keys) {
//            Long itemId = Long.parseLong(key.substring(VIEW_COUNT_PREFIX.length()));
//            String count = redisTemplate.opsForValue().getAndDelete(key);
//            if (count != null) {
//                viewCounts.put(itemId, Long.parseLong(count));
//            }
//        }
//
//        return viewCounts;
//    }
}
