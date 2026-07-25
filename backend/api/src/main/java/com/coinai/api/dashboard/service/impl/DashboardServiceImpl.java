package com.coinai.api.dashboard.service.impl;

import com.coinai.api.dashboard.dto.response.CategoryUsageResponse;
import com.coinai.api.dashboard.dto.response.DashboardResponse;
import com.coinai.api.dashboard.service.DashboardService;
import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.repository.MovementRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final MovementRepository movementRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public DashboardResponse getDashboard() {

        User user = authenticatedUserService.getCurrentUser();

        BigDecimal income = movementRepository.sumAmountByUserAndMovementType(
                user.getId(),
                MovementType.INCOME
        );

        BigDecimal expense = movementRepository.sumAmountByUserAndMovementType(
                user.getId(),
                MovementType.EXPENSE
        );

        BigDecimal balance = income.subtract(expense);

        List<CategoryUsageResponse> topCategories =
                movementRepository.findTopCategories(user.getId())
                        .stream()
                        .map(result -> CategoryUsageResponse.builder()
                                .categoryId((java.util.UUID) result[0])
                                .categoryName((String) result[1])
                                .movements((Long) result[2])
                                .build())
                        .toList();

        return DashboardResponse.builder()
                .income(income)
                .expense(expense)
                .balance(balance)
                .topCategories(topCategories)
                .build();

    }

}