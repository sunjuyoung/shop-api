package com.project.shop.payment.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckoutRequest {

    private Long orderId;
    private BigDecimal amount;
    private String orderName;
    //private String seed; //동일한 요청을 구분하기 위한 값 LocalDateTime.now().toString()

}
