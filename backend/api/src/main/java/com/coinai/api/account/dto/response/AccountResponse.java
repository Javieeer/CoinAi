package com.coinai.api.account.dto.response;

import com.coinai.api.account.AccountType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AccountResponse {

    private UUID id;

    private String name;

    private AccountType type;

    private String currency;

    private boolean archived;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}