package com.project.shop.like.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product_like_count")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductLikeCount {

    @Id
    private Long productId;

    private Long likeCount;

    @Version
    private Long version;

    public static ProductLikeCount init(Long productId, Long likeCount){
        ProductLikeCount productLikeCount = new ProductLikeCount();
        productLikeCount.productId = productId;
        productLikeCount.likeCount = likeCount;
        productLikeCount.version = 0L;
        return productLikeCount;
    }

    public void increase(){
        this.likeCount++;
    }

    public void decrease(){
        this.likeCount--;
    }


}
