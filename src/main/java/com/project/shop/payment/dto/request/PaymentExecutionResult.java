package com.project.shop.payment.dto.request;

import com.project.shop.payment.domain.enums.PaymentOrderStatus;
import com.project.shop.payment.vo.Failure;
import com.project.shop.payment.vo.PaymentExtraDetails;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentExecutionResult {
    private String paymentKey;
    private String orderId;
    private PaymentExtraDetails extraDetails;
    private Failure failure;
    private boolean isSuccess;
    private boolean isFailure;
    private boolean isUnknown;
    private boolean isRetryable;

    public PaymentOrderStatus paymentOrderStatus() {
        if (isSuccess()) {
            return PaymentOrderStatus.SUCCESS;
        } else if (isFailure()) {
            return PaymentOrderStatus.FAILURE;
        } else {
            throw new IllegalStateException("결제 (orderId: " + getOrderId() + ") 는 올바르지 않은 결제 상태입니다.");
        }
    }

    public boolean isSuccess() {
        return isSuccess;
    }
    public boolean isFailure() {
        return isFailure;
    }

}

