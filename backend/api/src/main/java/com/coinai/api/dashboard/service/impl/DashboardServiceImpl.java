package com.coinai.api.dashboard.service.impl;

import com.coinai.api.dashboard.dto.response.CategoryUsageResponse;
import com.coinai.api.dashboard.dto.response.DashboardResponse;
import com.coinai.api.dashboard.dto.response.FamilyDashboardResponse;
import com.coinai.api.dashboard.dto.response.FamilyMemberParticipationResponse;
import com.coinai.api.dashboard.dto.response.BarChartResponse;
import com.coinai.api.dashboard.dto.response.MonthlyTrendResponse;
import com.coinai.api.dashboard.dto.response.PieChartResponse;
import com.coinai.api.dashboard.service.DashboardService;
import com.coinai.api.family.entity.Family;
import com.coinai.api.family.entity.FamilyMember;
import com.coinai.api.family.repository.FamilyMemberRepository;
import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.repository.MovementRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final MovementRepository movementRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final FamilyMemberRepository familyMemberRepository;

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

    @Override
    public FamilyDashboardResponse getFamilyDashboard() {

        User user = authenticatedUserService.getCurrentUser();

        FamilyMember currentMember = familyMemberRepository
                .findByUserId(user.getId())
                .orElseThrow();

        Family family = currentMember.getFamily();

        List<FamilyMember> members =
                familyMemberRepository.findByFamily(family);

        List<UUID> userIds = members.stream()
                .map(member -> member.getUser().getId())
                .toList();

        BigDecimal incomes = movementRepository
                .sumAmountByUserIdsAndMovementType(
                        userIds,
                        MovementType.INCOME
                );

        BigDecimal expenses = movementRepository
                .sumAmountByUserIdsAndMovementType(
                        userIds,
                        MovementType.EXPENSE
                );

        BigDecimal balance = incomes.subtract(expenses);

        List<FamilyMemberParticipationResponse> participation =
                members.stream()
                        .map(member -> {

                                BigDecimal memberIncome =
                                        movementRepository
                                                .sumAmountByUserIdAndMovementType(
                                                        member.getUser().getId(),
                                                        MovementType.INCOME
                                                );

                                BigDecimal memberExpense =
                                        movementRepository
                                                .sumAmountByUserIdAndMovementType(
                                                        member.getUser().getId(),
                                                        MovementType.EXPENSE
                                                );

                                return FamilyMemberParticipationResponse.builder()
                                        .userId(member.getUser().getId())
                                        .userName(
                                                member.getUser().getFirstName()
                                                        + " "
                                                        + member.getUser().getLastName()
                                        )
                                        .balance(
                                                memberIncome.subtract(memberExpense)
                                        )
                                        .build();

                        })
                        .toList();

        List<CategoryUsageResponse> categories =
                movementRepository
                        .findTopCategoriesByUserIds(
                                userIds,
                                MovementType.EXPENSE
                        )
                        .stream()
                        .map(row ->
                                CategoryUsageResponse.builder()
                                        .categoryId((UUID) row[0])
                                        .categoryName((String) row[1])
                                        .movements((Long) row[2])
                                        .build()
                        )
                        .toList();

        return FamilyDashboardResponse.builder()
                .totalBalance(balance)
                .members(participation)
                .expensesByCategory(categories)
                .build();

    }

    @Override
    public BarChartResponse getBarChart() {

        User user = authenticatedUserService.getCurrentUser();

        BigDecimal income = movementRepository.sumAmountByUserIdAndMovementType(
                user.getId(),
                MovementType.INCOME
        );

        BigDecimal expense = movementRepository.sumAmountByUserIdAndMovementType(
                user.getId(),
                MovementType.EXPENSE
        );

        return BarChartResponse.builder()
                .income(income)
                .expense(expense)
                .build();

    }

    @Override
    public List<PieChartResponse> getPieChart() {

        User user = authenticatedUserService.getCurrentUser();

        return movementRepository
                .getPieChart(
                        user.getId(),
                        MovementType.EXPENSE
                )
                .stream()
                .map(row -> PieChartResponse.builder()
                        .categoryId((UUID) row[0])
                        .categoryName((String) row[1])
                        .amount((BigDecimal) row[2])
                        .build())
                .toList();

    }

    @Override
    public List<MonthlyTrendResponse> getMonthlyTrend() {

        User user = authenticatedUserService.getCurrentUser();

        List<Object[]> incomes = movementRepository.getMonthlyTrend(
                user.getId(),
                MovementType.INCOME
        );

        List<Object[]> expenses = movementRepository.getMonthlyTrend(
                user.getId(),
                MovementType.EXPENSE
        );

        Map<String, MonthlyTrendResponse> result = new LinkedHashMap<>();

        for (Object[] row : incomes) {

                int year = ((Number) row[0]).intValue();
                int month = ((Number) row[1]).intValue();

                String key = year + "-" + month;

                result.put(
                        key,
                        MonthlyTrendResponse.builder()
                                .year(year)
                                .month(month)
                                .income((BigDecimal) row[2])
                                .expense(BigDecimal.ZERO)
                                .build()
                );
        }

        for (Object[] row : expenses) {

                int year = ((Number) row[0]).intValue();
                int month = ((Number) row[1]).intValue();

                String key = year + "-" + month;

                MonthlyTrendResponse response = result.computeIfAbsent(
                        key,
                        k -> MonthlyTrendResponse.builder()
                                .year(year)
                                .month(month)
                                .income(BigDecimal.ZERO)
                                .expense(BigDecimal.ZERO)
                                .build()
                );

                response.setExpense((BigDecimal) row[2]);
        }

        return result.values().stream().toList();

    }
}