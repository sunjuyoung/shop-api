package com.project.shop.global.exception;

import com.project.shop.global.exception.enums.ExceptionCode;
import lombok.Getter;

@Getter
public class PaymentAlreadyException extends RuntimeException{

    private final ExceptionCode exceptionCode;


    public PaymentAlreadyException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
