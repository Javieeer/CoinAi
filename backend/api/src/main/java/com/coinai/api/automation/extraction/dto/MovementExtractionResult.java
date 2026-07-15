package com.coinai.api.automation.extraction.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MovementExtractionResult {

    private String movementType;

    private BigDecimal amount;

    private LocalDateTime date;

    private String merchant;

    private String recipient;

    private String account;

    private String category;

    private boolean needsUserInput;

    private List<String> missingFields;

}