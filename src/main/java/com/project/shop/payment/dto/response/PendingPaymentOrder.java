package com.project.shop.payment.dto.response;

import com.project.shop.payment.domain.enums.PaymentOrderStatus;
import lombok.Data;

@Data
public class PendingPaymentOrder {

    private Long paymentOrderId;
    private PaymentOrderStatus status;
    private Long amount;
    private int failedCount;
    private int threshold;
}
