package com.project.shop.hotproduct.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//인기글을 레디스에 저장
@Slf4j
@Repository
@RequiredArgsConstructor
public class HotProductListRepository {

    private final StringRedisTemplate redisTemplate;

    //hot-product::list::{yyyyMMdd}
    private static final String KEY_FORMAT = "hot-product::list::%s";

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    //인기상품 리스트 레디스에 저장
    public void add(Long productId, LocalDateTime time, Long score, Long limit, Duration ttl){
        redisTemplate
                .executePipelined((RedisCallback<?>) action->{
                    StringRedisConnection conn = (StringRedisConnection) action;
                    String key = generateKey(time);
                    conn.zAdd(key, score, String.valueOf(productId)); //sorted set에 productId를 score로 추가
                    conn.zRemRange(key, 0, - limit - 1); //상위 limit개수 이상의 데이터는 삭제
                    conn.expire(key, ttl.toSeconds()); //ttl 지정
                    return null;
                });

    }

    private String generateKey(LocalDateTime time){
        return generateKey(TIME_FORMATTER.format(time));
    }

    private String generateKey(String dateStr){
        return KEY_FORMAT.formatted(dateStr);
    }


    public void remove(Long productId, LocalDateTime time){
        String key = generateKey(time);
        redisTemplate.opsForZSet().remove(key, String.valueOf(productId));
    }

    public List<Long> readAll(String dateStr) {
        String key = generateKey(dateStr);
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1)
                .stream()
                .peek(tuple -> log.info("productId={}, score={}", tuple.getValue(), tuple.getScore()))
                .map(ZSetOperations.TypedTuple::getValue)
                .map(Long::parseLong)
                .toList();
    }
}
