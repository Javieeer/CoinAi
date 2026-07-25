package com.coinai.api.automation.extraction.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FreeTextClassificationResponse {

    private String movementType;

    private BigDecimal amount;

    private String category;

    private String paymentMethod;

    private String merchant;

    private String confidence;

}