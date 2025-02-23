package com.project.shop.comment.repository;

import com.project.shop.comment.entity.ProductCommentCount;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductCommentCountRepository extends JpaRepository<ProductCommentCount, Long> {


    @Query(
            value = "update product_comment_count set comment_count = comment_count + 1 where product_id = :productId",
            nativeQuery = true
    )
    @Modifying
    int increase(@Param("productId") Long productId);

    @Query(
            value = "update product_comment_count set comment_count = GREATEST(comment_count - 1, 0)  where product_id = :productId",
            nativeQuery = true
    )
    @Modifying
    int decrease(@Param("productId") Long productId);
}
