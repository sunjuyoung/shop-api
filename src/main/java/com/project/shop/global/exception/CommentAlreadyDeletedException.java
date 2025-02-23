package com.project.shop.global.exception;

import com.project.shop.global.exception.enums.ExceptionCode;
import lombok.Getter;

@Getter
public class CommentAlreadyDeletedException extends RuntimeException{

    private   final ExceptionCode exceptionCode;

    public CommentAlreadyDeletedException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

}
