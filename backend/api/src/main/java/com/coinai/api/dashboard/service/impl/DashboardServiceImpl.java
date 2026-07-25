package com.coinai.api.dashboard.service.impl;

import com.coinai.api.dashboard.dto.response.CategoryUsageResponse;
import com.coinai.api.dashboard.dto.response.DashboardResponse;
import com.coinai.api.dashboard.dto.response.FamilyDashboardResponse;
import com.coinai.api.dashboard.dto.response.FamilyMemberParticipationResponse;
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
import java.util.List;
import java.util.UUID;

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
}