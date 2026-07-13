package com.coinai.api.movement.dto.request;

import com.coinai.api.movement.MovementSource;
import com.coinai.api.movement.MovementStatus;
import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.MovementVisibility;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateMovementRequest {

    @NotNull
    private MovementType movementType;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    private String description;

    @NotNull
    private LocalDateTime movementDate;

    @NotNull
    private UUID accountId;

    private UUID destinationAccountId;

    private UUID paymentMethodId;

    @NotNull
    private UUID categoryId;

    private UUID subcategoryId;

    @NotNull
    private MovementVisibility visibility;

    @NotNull
    private MovementStatus status;

    @NotNull
    private MovementSource source;

}