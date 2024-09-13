package com.project.shop.global.exception;

import com.project.shop.global.exception.enums.ExceptionCode;
import lombok.Getter;

@Getter
public class PasswordNotMatchException extends RuntimeException{


    private final ExceptionCode exceptionCode;

    public PasswordNotMatchException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;

    }
}
