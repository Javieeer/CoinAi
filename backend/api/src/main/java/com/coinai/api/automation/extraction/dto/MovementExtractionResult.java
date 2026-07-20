package com.coinai.api.automation.extraction.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MovementExtractionResult {

    private String bank;

    private String notificationType;

    private String movementType;

    private BigDecimal amount;

    private String currency;

    private LocalDateTime date;

    private String merchant;

    private String recipient;

    private String paymentMethod;

    private String account;

    private String cardLastDigits;

    private String accountLastDigits;

    private Boolean installmentPurchase;

    private Integer installments;

    private String category;

    private Boolean needsUserInput;

    private List<String> missingFields;

    private String confidence;

    private String rawMerchant;

}