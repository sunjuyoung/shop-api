package com.project.shop.customer.dto.request;

import com.project.shop.global.exception.response.ErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignUpDTO {

    @Email(message= ErrorMessage.INVALID_EMAIL)
    private String email;

    @Size(min = 4, max = 20, message = ErrorMessage.INVALID_PASSWORD)
    private String password;

    @Size(min = 4, max = 20,message = ErrorMessage.INVALID_INPUT_VALUE)
    private String nickname;

}
