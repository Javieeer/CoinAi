package com.coinai.api.budget.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class UpdateBudgetRequest {

    private UUID familyId;

    @NotNull
    private UUID categoryId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private Short month;

    @NotNull
    private Short year;

}