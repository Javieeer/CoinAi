package com.coinai.api.goal.dto.response;

import com.coinai.api.goal.GoalStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class GoalResponse {

    private UUID id;

    private UUID familyId;

    private String name;

    private String description;

    private BigDecimal targetAmount;

    private BigDecimal currentAmount;

    private BigDecimal remainingAmount;

    private Integer progressPercentage;

    private Integer remainingMonths;

    private BigDecimal recommendedMonthlySaving;

    private LocalDate targetDate;

    private GoalStatus status;

}