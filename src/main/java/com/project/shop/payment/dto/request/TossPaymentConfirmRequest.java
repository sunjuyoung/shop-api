package com.project.shop.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TossPaymentConfirmRequest {


    private String paymentKey;
    private String orderId;
    private Long amount;
}
