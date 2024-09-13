package com.project.shop.global.exception;

import com.project.shop.global.exception.enums.ExceptionCode;
import lombok.Getter;

@Getter
public class PaymentAmountException  extends RuntimeException{

    private final ExceptionCode exceptionCode;
    private final String message;

    public PaymentAmountException(ExceptionCode exceptionCode, String message) {
        this.exceptionCode = exceptionCode;
        this.message = message;
    }
}
