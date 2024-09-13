package com.project.shop.orderItem.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

    private Long productId;
    private int quantity;
}
