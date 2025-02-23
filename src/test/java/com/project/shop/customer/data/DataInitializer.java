package com.project.shop.customer.data;


import com.project.shop.customer.entity.Customer;
import com.project.shop.customer.entity.enums.Grade;
import com.project.shop.customer.entity.enums.Roles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ActiveProfiles("dev")
@SpringBootTest
public class DataInitializer {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    TransactionTemplate transactionTemplate;
    CountDownLatch latch = new CountDownLatch(EXECUTE_COUNT);


    static final int BULK_INSERT_SIZE = 500;
    static final int EXECUTE_COUNT = 500;

    @Test
    void initialize() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0; i< EXECUTE_COUNT; i++){
            executorService.submit(()->{
                insert();
                latch.countDown();
                System.out.println("latch count =" +latch.getCount());
            });
        }
        latch.await();
        executorService.shutdown();

    }

    void insert(){
        transactionTemplate.executeWithoutResult(transactionStatus ->{
            for(int i=0; i<BULK_INSERT_SIZE; i++){

                entityManager.persist(
                        Customer.createCustomer("user"+i+"@test.com", "1234","user"+i,
                                "010-1234-1234", Grade.BRONZE, List.of(Roles.CUSTOMER))
                );
            }
        });


    }
}
