package com.project.shop.order.repository;

import com.project.shop.category.repository.CustomCategoryRepository;
import com.project.shop.order.entity.Order;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long>, CustomOrderRepository {



    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findAllAndOrderItemsById(Long id);
}
