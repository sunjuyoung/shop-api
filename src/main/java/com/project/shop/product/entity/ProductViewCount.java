package com.project.shop.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product_view_count")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductViewCount {

    @Id
    private Long productId;

    @Column(nullable = false)
    private Long viewCount;

    public static ProductViewCount create(Long productId, Long viewCount){
        ProductViewCount productViewCount = new ProductViewCount();
        productViewCount.productId = productId;
        productViewCount.viewCount = viewCount;
        return productViewCount;
    }

}
