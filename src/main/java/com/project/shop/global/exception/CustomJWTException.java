package com.project.shop.global.exception;

import com.project.shop.global.exception.enums.ExceptionCode;
import lombok.Getter;

@Getter
public class CustomJWTException extends RuntimeException{


    private final ExceptionCode exceptionCode;

    public CustomJWTException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;

    }
}
