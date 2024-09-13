package com.project.shop.payment.repository;

import com.project.shop.payment.domain.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PaymentEventRepositoryTest {

    @Autowired
    PaymentEventRepository paymentEventRepository;


    @Test
    public void test(){
        String key = "14_testNumber";
        PaymentEvent paymentEvent = paymentEventRepository.findAllByOrderKey(key).get();

         paymentEvent.getPaymentOrders().forEach(paymentOrder -> {
             System.out.println(paymentOrder.getOrderKey());
         });
    }

}