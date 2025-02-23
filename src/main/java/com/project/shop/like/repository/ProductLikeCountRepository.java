package com.project.shop.like.repository;

import com.project.shop.like.entity.ProductLikeCount;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductLikeCountRepository extends JpaRepository<ProductLikeCount, Long> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ProductLikeCount> findByProductId(Long productId);


    @Query(
            value = "update product_like_count set like_count = like_count + 1 where product_id = :productId",
            nativeQuery = true
    )
    @Modifying
    int increase(@Param("productId") Long productId);

    @Query(
            value = "update product_like_count set like_count = like_count - 1 where product_id = :productId",
            nativeQuery = true
    )
    @Modifying
    int decrease(@Param("productId") Long productId);
}
