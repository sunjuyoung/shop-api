package com.project.shop.hotproduct.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class HotProductListRepositoryTest {

    @Autowired
    HotProductListRepository hotProductListRepository;

    @Test
    void addTest(){
        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        long limit = 3;

        hotProductListRepository.add(1L, time, 2L, limit, Duration.ofSeconds(20));
        hotProductListRepository.add(2L, time, 3L, limit, Duration.ofSeconds(20));
        hotProductListRepository.add(3L, time, 1L, limit, Duration.ofSeconds(20));
        hotProductListRepository.add(4L, time, 5L, limit, Duration.ofSeconds(20));
        hotProductListRepository.add(5L, time, 4L, limit, Duration.ofSeconds(20));
        List<Long> longs = hotProductListRepository.readAll("20250101");




    }

}