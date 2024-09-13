package com.project.shop.order.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderViewResponseDTO {

    private Long orderId;
    private BigDecimal amount;
    private String orderName;
}
