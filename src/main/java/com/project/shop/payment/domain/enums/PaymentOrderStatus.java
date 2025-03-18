package com.project.shop.payment.domain.enums;

import lombok.Getter;

@Getter
public enum PaymentOrderStatus {

    NOT_STARTED("결제 승인 시작 전"),
    EXECUTING("결제 승인 중"),
    SUCCESS("결제 성공"),
    FAILURE("결제 실패");

    private String description;

     PaymentOrderStatus(String description) {
        this.description = description;
    }

}
