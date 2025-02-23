package com.project.shop.global.exception;

import com.project.shop.global.exception.enums.ExceptionCode;
import lombok.Getter;

@Getter
public class NotFoundLike extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public NotFoundLike(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

}
