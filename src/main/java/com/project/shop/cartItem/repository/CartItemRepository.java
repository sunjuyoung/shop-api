package com.project.shop.cartItem.repository;

import com.project.shop.cartItem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long>,CustomCartItemRepository {


}
