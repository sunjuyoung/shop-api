package com.project.shop.order.repository;

import com.project.shop.order.entity.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.project.shop.order.entity.QOrder.*;
import static com.project.shop.orderItem.entity.QOrderItem.*;
import static com.project.shop.product.entity.QProduct.*;

public class OrderRepositoryImpl implements CustomOrderRepository{


    private final JPAQueryFactory queryFactory;


    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Order findOrderWithOrderItemAndProductById(Long orderId) {

        Order order1 = queryFactory.select(order)
                .from(order)
                .innerJoin(order.orderItems, orderItem).fetchJoin()
                .innerJoin(orderItem.product, product).fetchJoin()
                .where(order.id.eq(orderId))
                .fetchOne();
        return order1;
    }
}
