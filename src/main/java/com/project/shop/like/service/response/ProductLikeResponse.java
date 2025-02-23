package com.project.shop.like.service.response;

import com.project.shop.like.entity.ProductLike;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductLikeResponse {

    private Long id;
    private Long productId;
    private Long customerId;
    private LocalDateTime createdAt;

    public static ProductLikeResponse create(ProductLike productLike) {
        ProductLikeResponse productLikeResponse = new ProductLikeResponse();
        productLikeResponse.id = productLike.getId();
        productLikeResponse.productId = productLike.getProduct().getId();
        productLikeResponse.customerId = productLike.getCustomer().getId();
        productLikeResponse.createdAt = productLike.getCreatedAt();
        return productLikeResponse;
    }
}
