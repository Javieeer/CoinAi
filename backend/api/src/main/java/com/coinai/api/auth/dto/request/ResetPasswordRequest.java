package com.coinai.api.auth.dto.request;

import com.coinai.api.validation.annotation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank(message = "Token is required.")
    private String token;

    @NotBlank(message = "Password is required.")
    @ValidPassword
    private String newPassword;

}