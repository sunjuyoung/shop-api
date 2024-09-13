package com.project.shop.cartItem.entity;

import com.project.shop.cart.entity.Cart;
import com.project.shop.global.domain.BaseTime;
import com.project.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Getter
@Table(name = "cart_item")
public class CartItem extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public void changeQty(int qty){
        this.quantity = qty;
    }
}
