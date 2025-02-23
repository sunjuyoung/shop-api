package com.project.shop.like.entity;

import com.project.shop.customer.entity.Customer;
import com.project.shop.global.domain.BaseTime;
import com.project.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductLike extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;


    public static ProductLike create(Product product, Customer customer) {
        ProductLike productLike = new ProductLike();
        productLike.product = product;
        productLike.customer = customer;
        return productLike;
    }


}
