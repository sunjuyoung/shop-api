package com.project.shop.global.exception;

import com.project.shop.global.exception.enums.ExceptionCode;
import lombok.Getter;

@Getter
public class AlreadyExistEmailException extends RuntimeException{

    private   final ExceptionCode exceptionCode;

    public AlreadyExistEmailException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
