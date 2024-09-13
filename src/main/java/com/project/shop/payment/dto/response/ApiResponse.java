package com.project.shop.payment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {

    private String message;
    private int status;
    private T data;



    public ApiResponse(String message, HttpStatus status, T s) {
        this.message = message;
        this.status = status.value();
        this.data = s;
    }
}
