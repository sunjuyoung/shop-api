package com.project.shop.order.repository;

import com.project.shop.order.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderRepositoryImplTest {

    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void testOrderById(){
        Long orderId = 14L;

        Order order = orderRepository.findOrderWithOrderItemAndProductById(orderId);


        log.info(order.getOrderItems().get(0).getId().toString());
        log.info(order.getOrderItems().get(0).getProduct().getName());
    }

}