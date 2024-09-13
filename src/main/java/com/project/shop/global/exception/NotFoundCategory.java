package com.project.shop.global.exception;

import com.project.shop.global.exception.enums.ExceptionCode;
import lombok.Getter;

@Getter
public class NotFoundCategory extends RuntimeException{

    private final ExceptionCode exceptionCode;

    public NotFoundCategory(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;

    }
}
