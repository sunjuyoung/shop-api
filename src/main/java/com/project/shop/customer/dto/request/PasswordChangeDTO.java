package com.project.shop.customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordChangeDTO {

    @NotNull
    private Long id;

    @NotBlank(message = "비밀번호 값을 다시 확인해주세요")
    private String newPassword;

    @NotBlank(message = "비밀번호 값을 다시 확인해주세요")
    private String confirmNewPassword;
}
