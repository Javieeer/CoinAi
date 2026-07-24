package com.coinai.api.account.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class AccountBalanceResponse {

    private UUID accountId;

    private String accountName;

    private BigDecimal currentBalance;

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

}