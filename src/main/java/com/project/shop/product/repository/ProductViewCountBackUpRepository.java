package com.project.shop.product.repository;

import com.project.shop.product.entity.ProductViewCount;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductViewCountBackUpRepository extends JpaRepository<ProductViewCount, Long> {


    @Query(
            value = "update product_view_count set view_count = :viewCount " +
                    " where product_id = :productId and view_count < :viewCount",
            nativeQuery = true
    )
    @Modifying
    int updateViewCount(@Param("viewCount") Long viewCount, @Param("productId") Long productId);
}
