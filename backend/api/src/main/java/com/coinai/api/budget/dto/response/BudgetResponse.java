package com.coinai.api.budget.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponse {

    private UUID id;

    private UUID familyId;

    private UUID categoryId;

    private BigDecimal amount;

    private Short month;

    private Short year;

    // Calculados

    private BigDecimal spent;

    private BigDecimal remaining;

    private Integer percentage;

}