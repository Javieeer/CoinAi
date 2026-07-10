package com.coinai.api.account.dto.request;

import com.coinai.api.account.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountRequest {

    @NotBlank(message = "Account name is required.")
    @Size(max = 100, message = "Account name cannot exceed 100 characters.")
    private String name;

    @NotNull(message = "Account type is required.")
    private AccountType type;

    @NotBlank(message = "Currency is required.")
    @Size(max = 10, message = "Currency cannot exceed 10 characters.")
    private String currency;

}