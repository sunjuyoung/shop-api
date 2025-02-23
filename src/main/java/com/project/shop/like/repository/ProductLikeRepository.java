package com.project.shop.like.repository;

import com.project.shop.customer.entity.Customer;
import com.project.shop.like.entity.ProductLike;
import com.project.shop.product.entity.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {


    @EntityGraph(attributePaths = {"product", "customer"})
    @Query(value = "select pl from ProductLike pl where pl.customer.id = :customerId and pl.product.id = :productId")
    Optional<ProductLike> findByUserIdAndProductId(@Param("customerId") Long customerId, @Param("productId") Long productId);

    Optional<ProductLike> findByCustomerAndProduct(Customer customer, Product product);
}
