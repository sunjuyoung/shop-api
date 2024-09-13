package com.project.shop.cartItem.dto.response;

import com.project.shop.cartItem.entity.CartItem;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartItemListDTO {


    private Long cartId;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private int quantity;

    private Long cartItemId;


    private List<String> productImages;

    private String imageUrl;


    public CartItemListDTO(Long cartItemId,  String productImages, Long productId,String productName,BigDecimal price, int quantity) {
        this.cartItemId = cartItemId;
        this.imageUrl = productImages;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public CartItemListDTO(CartItem cartItem){
        this.cartId = cartItem.getCart().getId();
        this.imageUrl = cartItem.getProduct().getProductImages().get(0).getFileName();
    }

    @QueryProjection
    public CartItemListDTO(Long cartItemId, Long productId, String productName, BigDecimal price, int quantity, String imageUrl) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }
}
