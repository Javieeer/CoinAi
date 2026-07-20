package com.coinai.api.automation.learning.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateMerchantRuleRequest {

    @NotBlank
    private String rawMerchant;

    @NotBlank
    private String normalizedMerchant;

    @NotNull
    private UUID categoryId;

}