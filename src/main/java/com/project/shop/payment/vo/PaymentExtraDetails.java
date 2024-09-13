package com.project.shop.payment.vo;

import com.project.shop.payment.domain.enums.PSPConfirmStatus;
import com.project.shop.payment.domain.enums.PaymentMethod;
import com.project.shop.payment.domain.enums.PaymentType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public  class PaymentExtraDetails {

    private PaymentType type;
    private PaymentMethod method;
    private LocalDateTime approvedAt;
    private String orderName;
    private PSPConfirmStatus pspConfirmStatus;
    private Long totalAmount;
    private String pspRawData; //승인결과
}