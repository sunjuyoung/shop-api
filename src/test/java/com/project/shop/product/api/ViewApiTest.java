package com.project.shop.product.api;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ActiveProfiles("dev")
public class ViewApiTest {


    RestClient restClient = RestClient.create("http://localhost:8080/products");

    @Test
    void viewTest() throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CountDownLatch latch = new CountDownLatch(100);

            for(int i=0; i<100; i++) {
                executorService.submit(() -> {
                    restClient.get()
                            .uri("/{productId}", 110L)
                            .retrieve();
                    latch.countDown();
                });
            }




        latch.await();

        Long count = restClient.get()
                .uri("/view/{productId}", 11L)
                .retrieve()
                .body(Long.class);
        System.out.println("count = " + count);
    }

    //스레드 사용하지 않고
    //단순 for문으로 100번 호출
    @Test
    public void test2(){
        for(int j=101; j<111; j++){
            for(int i=0; i<50; i++) {
                restClient.get()
                        .uri("/{productId}", (long)j)
                        .retrieve();
            }
        }




    }
}
