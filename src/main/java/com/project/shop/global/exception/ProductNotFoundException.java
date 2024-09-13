package com.project.shop.global.exception;

import com.project.shop.global.exception.enums.ExceptionCode;
import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException{

    private final ExceptionCode exceptionCode;

    public ProductNotFoundException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;

    }


}
