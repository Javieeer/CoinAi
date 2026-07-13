package com.coinai.api.movement.dto.response;

import com.coinai.api.movement.MovementSource;
import com.coinai.api.movement.MovementStatus;
import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.MovementVisibility;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class MovementResponse {

    private UUID id;

    private MovementType movementType;

    private BigDecimal amount;

    private String description;

    private LocalDateTime movementDate;

    private UUID accountId;

    private UUID destinationAccountId;

    private UUID paymentMethodId;

    private UUID categoryId;

    private UUID subcategoryId;

    private MovementVisibility visibility;

    private MovementStatus status;

    private MovementSource source;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}