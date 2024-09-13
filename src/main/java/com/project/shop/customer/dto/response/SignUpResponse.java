package com.project.shop.customer.dto.response;

import lombok.Data;

@Data
public class SignUpResponse {

    private Long id;
    private String message;


    public SignUpResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}
