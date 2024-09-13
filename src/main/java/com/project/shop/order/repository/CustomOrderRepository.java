package com.project.shop.order.repository;

import com.project.shop.order.entity.Order;

public interface CustomOrderRepository {

    Order findOrderWithOrderItemAndProductById(Long orderId);
}
