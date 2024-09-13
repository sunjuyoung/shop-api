package com.project.shop.global.exception;

import com.project.shop.global.exception.enums.ExceptionCode;
import lombok.Getter;

@Getter
public class NotEnoughStockException extends RuntimeException{

    private final ExceptionCode exceptionCode;

    public NotEnoughStockException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
