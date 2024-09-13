package com.project.shop.global.exception;

import lombok.Getter;

@Getter
public class PSPConfirmationException extends RuntimeException{

    private String errorCode;
    private String errorMessage;

    private boolean isRetryableError;


    public PSPConfirmationException(String errorCode, String errorMessage, boolean isRetryableError) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.isRetryableError = isRetryableError;
    }
}
