package com.coinai.api.notification.service.impl;

import com.coinai.api.budget.entity.Budget;
import com.coinai.api.budget.repository.BudgetRepository;
import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.entity.Movement;
import com.coinai.api.movement.repository.MovementRepository;
import com.coinai.api.notification.BudgetAlertLevel;
import com.coinai.api.notification.service.BudgetAlertService;
import com.coinai.api.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BudgetAlertServiceImpl implements BudgetAlertService {

    private final BudgetRepository budgetRepository;

    private final MovementRepository movementRepository;

    private final NotificationService notificationService;

    @Override
    public void checkBudgetAlerts(
            Movement movement
    ) {

        if (movement.getMovementType() != MovementType.EXPENSE) {
            return;
        }

        Budget budget = budgetRepository
                .findByUserIdAndCategoryIdAndMonthAndYear(
                        movement.getUser().getId(),
                        movement.getCategory().getId(),
                        (short) movement.getMovementDate().getMonthValue(),
                        (short) movement.getMovementDate().getYear()
                )
                .orElse(null);

        if (budget == null) {
            return;
        }

        LocalDate firstDay = LocalDate.of(
                budget.getYear(),
                budget.getMonth(),
                1
        );

        LocalDate lastDay = firstDay.withDayOfMonth(
                firstDay.lengthOfMonth()
        );

        BigDecimal spent =
                movementRepository.sumAmountByUserAndCategoryAndTypeAndDateBetween(
                        movement.getUser().getId(),
                        movement.getCategory().getId(),
                        MovementType.EXPENSE,
                        firstDay.atStartOfDay(),
                        lastDay.atTime(23,59,59)
                );

        int percentage =
                spent.multiply(BigDecimal.valueOf(100))
                        .divide(
                                budget.getAmount(),
                                0,
                                RoundingMode.HALF_UP
                        )
                        .intValue();

        if (percentage >= 100) {

            notificationService.notifyBudgetAlert(
                    movement.getUser(),
                    movement.getCategory().getName(),
                    BudgetAlertLevel.FULL,
                    percentage
            );

        }
        else if (percentage >= 80) {

            notificationService.notifyBudgetAlert(
                    movement.getUser(),
                    movement.getCategory().getName(),
                    BudgetAlertLevel.EIGHTY_PERCENT,
                    percentage
            );

        }

    }

}