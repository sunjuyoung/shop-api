package com.project.shop.like.api;

import com.project.shop.like.entity.ProductLike;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@ActiveProfiles("dev")
public class LikeApiTest {
    String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzb2NpYWwiOmZhbHNlLCJuaWNrbmFtZSI6InRlc3QxMjMzIiwiaWQiOjMsInJvbGVOYW1lcyI6WyJDVVNUT01FUiJdLCJlbWFpbCI6InRlc3RAdGVzdC5jb20iLCJpYXQiOjE3NDAyOTE1MDQsImV4cCI6MTc0MDMzNDcwNH0.SCsHoRHwVnZrn5Oc3_SB_P384bgg5iADwIB6F4x_M7I";
    RestClient restClient = RestClient.builder()
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
            .baseUrl("http://localhost:9090")
            .build();

    void like(Long productId, Long customerId){
        restClient.post()
                .uri("/v1/product-likes/products/{productId}/customer/{customerId}" , productId, customerId)
                .retrieve();
    }
    @Test
    void likePerformanceTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        likePerformanceTest(executorService, 11L, "optimistic-lock");
    }

    void likePerformanceTest(ExecutorService executorService, Long productId, String lockType) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(400);
        System.out.println(lockType + " start");

        like(productId, 2L);

        long start = System.nanoTime();
        for(int i=12; i < 400; i++) {
            long userId = i + 2;
            executorService.submit(() -> {
                like(productId, userId);
                latch.countDown();
            });
        }

        latch.await();

        long end = System.nanoTime();

        System.out.println("lockType = " + lockType + ", time = " + (end - start) / 1000000 + "ms");
        System.out.println(lockType + " end");

        Long count = restClient.get()
                .uri("/v1/product-likes/products/{productId}/count", productId)
                .retrieve()
                .body(Long.class);

        System.out.println("count = " + count);
    }
}
