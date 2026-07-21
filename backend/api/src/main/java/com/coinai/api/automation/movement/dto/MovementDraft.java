package com.coinai.api.automation.movement.dto;

import com.coinai.api.automation.parser.BankType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@lombok.ToString
public class MovementDraft {

    private BigDecimal amount;

    private String description;

    private String sourceAccount;

    private String destinationAccount;

    private LocalDateTime occurredAt;

    private BankType bankType;

    private String rawBody;

}