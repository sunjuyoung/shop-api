package com.project.shop.payment.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckoutRequest {

    private Long orderId;
    private BigDecimal amount;
    private String orderName;

}
