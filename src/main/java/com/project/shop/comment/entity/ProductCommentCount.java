package com.project.shop.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "product_comment_count")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCommentCount {

    @Id
    private Long productId;

    @Column(nullable = false)
    private Long commentCount;


    public static ProductCommentCount init(Long productId, Long commentCount){
        ProductCommentCount productCommentCount = new ProductCommentCount();
        productCommentCount.productId = productId;
        productCommentCount.commentCount = commentCount;
        return productCommentCount;
    }

    public void increase(){
        this.commentCount++;
    }

    public void decrease(){
        if(this.commentCount > 0){
            this.commentCount--;
        }

    }
}
