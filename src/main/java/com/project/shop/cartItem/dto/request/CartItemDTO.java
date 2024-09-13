package com.project.shop.cartItem.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {

    private String email;
    private int quantity;
    private Long productId;
    private Long cartId;
    private Long customerId;
    private Long cartItemId; //이미 만들어진 장바구니 아이템을 수량 수정할 때 사용
}
