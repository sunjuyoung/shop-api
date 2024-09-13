package com.project.shop.global.exception.response;

import com.project.shop.global.exception.enums.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String code;
    private String message;

    private String messageDetail;


    @Builder
    public ErrorResponse(final ExceptionCode code) {
        this.status = code.getStatus();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.messageDetail = code.getMessage();
    }

    public ErrorResponse(ExceptionCode code, String message) {
        this.status = code.getStatus();
        this.code = code.getCode();
        this.message = message;
    }

    public ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }



    @Builder
    public static ErrorResponse of(final ExceptionCode code) {
        return new ErrorResponse(code);
    }


    @Builder
    public static ErrorResponse of(final ExceptionCode code,String messageDetail) {
        return new ErrorResponse(code,messageDetail);
    }

    public ErrorResponse(String message) {
        this.message = message;
    }
}
