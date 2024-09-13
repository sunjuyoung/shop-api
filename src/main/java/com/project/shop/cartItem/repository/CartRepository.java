package com.project.shop.cartItem.repository;

import com.project.shop.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.customer.id = :id")
    public Optional<Cart> getCartByCustomer(@Param("id")Long id);

}
