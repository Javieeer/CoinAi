package com.coinai.api.movement.filter;

import com.coinai.api.movement.MovementType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class MovementFilterRequest {

    private MovementType movementType;

    private LocalDate startDate;

    private LocalDate endDate;

    private UUID categoryId;

    private UUID paymentMethodId;

    private UUID tagId;

    private UUID userId;

}