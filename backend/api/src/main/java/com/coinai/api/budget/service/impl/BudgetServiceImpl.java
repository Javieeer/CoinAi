package com.coinai.api.budget.service.impl;

import com.coinai.api.budget.dto.request.CreateBudgetRequest;
import com.coinai.api.budget.dto.request.UpdateBudgetRequest;
import com.coinai.api.budget.dto.response.BudgetResponse;
import com.coinai.api.budget.entity.Budget;
import com.coinai.api.budget.exception.BudgetAlreadyExistsException;
import com.coinai.api.budget.exception.BudgetNotFoundException;
import com.coinai.api.budget.mapper.BudgetMapper;
import com.coinai.api.budget.repository.BudgetRepository;
import com.coinai.api.budget.service.BudgetService;
import com.coinai.api.category.entity.Category;
import com.coinai.api.category.exception.CategoryNotFoundException;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.family.entity.Family;
import com.coinai.api.family.exception.FamilyNotFoundException;
import com.coinai.api.family.repository.FamilyRepository;
import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.repository.MovementRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final CategoryRepository categoryRepository;
    private final FamilyRepository familyRepository;
    private final MovementRepository movementRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public BudgetResponse create(CreateBudgetRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        boolean exists;

        if (request.getFamilyId() == null) {
        exists = budgetRepository
                .existsByUserIdAndFamilyIsNullAndCategoryIdAndMonthAndYear(
                        user.getId(),
                        request.getCategoryId(),
                        request.getMonth(),
                        request.getYear()
                );
        } else {
        exists = budgetRepository
                .existsByUserIdAndFamilyIdAndCategoryIdAndMonthAndYear(
                        user.getId(),
                        request.getFamilyId(),
                        request.getCategoryId(),
                        request.getMonth(),
                        request.getYear()
                );
        }

        if (exists) {
        throw new BudgetAlreadyExistsException();
        }

        Category category = categoryRepository.findByIdAndUserId(
                request.getCategoryId(),
                user.getId()
        ).orElseThrow(CategoryNotFoundException::new);

        Family family = null;

        if (request.getFamilyId() != null) {
            family = familyRepository.findById(request.getFamilyId())
                    .orElseThrow(FamilyNotFoundException::new);
        }

        Budget budget = budgetMapper.toEntity(request);

        budget.setUser(user);
        budget.setCategory(category);
        budget.setFamily(family);
        budget.setCreatedAt(LocalDateTime.now());
        budget.setUpdatedAt(LocalDateTime.now());

        budget = budgetRepository.save(budget);

        return enrichResponse(budgetMapper.toResponse(budget), budget);

    }

    @Override
    public List<BudgetResponse> findAll(
            Short month,
            Short year
    ) {

        User user = authenticatedUserService.getCurrentUser();

        List<Budget> budgets = budgetRepository.findByUserIdAndMonthAndYear(
                user.getId(),
                month,
                year
        );

        return budgets.stream()
                .map(budget -> enrichResponse(
                        budgetMapper.toResponse(budget),
                        budget
                ))
                .toList();

    }

    private BudgetResponse enrichResponse(
            BudgetResponse response,
            Budget budget
    ) {

        LocalDate firstDay = LocalDate.of(
                budget.getYear(),
                budget.getMonth(),
                1
        );

        LocalDate lastDay = firstDay.withDayOfMonth(
                firstDay.lengthOfMonth()
        );

        BigDecimal spent = movementRepository
                .sumAmountByUserAndCategoryAndTypeAndDateBetween(
                        budget.getUser().getId(),
                        budget.getCategory().getId(),
                        MovementType.EXPENSE,
                        firstDay.atStartOfDay(),
                        lastDay.atTime(23, 59, 59)
                );

        BigDecimal remaining = budget.getAmount().subtract(spent);

        int percentage = 0;

        if (budget.getAmount().compareTo(BigDecimal.ZERO) > 0) {

            percentage = spent
                    .multiply(BigDecimal.valueOf(100))
                    .divide(
                            budget.getAmount(),
                            0,
                            RoundingMode.HALF_UP
                    )
                    .intValue();

        }

        response.setSpent(spent);
        response.setRemaining(remaining);
        response.setPercentage(percentage);

        return response;

    }

    @Override
        public BudgetResponse update(
                UUID id,
                UpdateBudgetRequest request
        ) {

        User user = authenticatedUserService.getCurrentUser();

        Budget budget = budgetRepository.findByIdAndUserId(
                id,
                user.getId()
        ).orElseThrow(BudgetNotFoundException::new);

        boolean exists;

        if (request.getFamilyId() == null) {
                exists = budgetRepository
                        .existsByUserIdAndFamilyIsNullAndCategoryIdAndMonthAndYear(
                                user.getId(),
                                request.getCategoryId(),
                                request.getMonth(),
                                request.getYear()
                        );
        } else {
                exists = budgetRepository
                        .existsByUserIdAndFamilyIdAndCategoryIdAndMonthAndYear(
                                user.getId(),
                                request.getFamilyId(),
                                request.getCategoryId(),
                                request.getMonth(),
                                request.getYear()
                        );
        }

        boolean changed =
                !budget.getCategory().getId().equals(request.getCategoryId())
                || !budget.getMonth().equals(request.getMonth())
                || !budget.getYear().equals(request.getYear())
                || (
                        (budget.getFamily() == null && request.getFamilyId() != null)
                        || (budget.getFamily() != null
                                && !budget.getFamily().getId().equals(request.getFamilyId()))
                );

        if (changed && exists) {
                throw new BudgetAlreadyExistsException();
        }

        Category category = categoryRepository.findByIdAndUserId(
                request.getCategoryId(),
                user.getId()
        ).orElseThrow(CategoryNotFoundException::new);

        Family family = null;

        if (request.getFamilyId() != null) {
                family = familyRepository.findById(request.getFamilyId())
                        .orElseThrow(FamilyNotFoundException::new);
        }

        budget.setCategory(category);
        budget.setFamily(family);
        budget.setAmount(request.getAmount());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());
        budget.setUpdatedAt(LocalDateTime.now());

        budget = budgetRepository.save(budget);

        return enrichResponse(
                budgetMapper.toResponse(budget),
                budget
        );

        }

    @Override
    public void delete(UUID id) {

        User user = authenticatedUserService.getCurrentUser();

        Budget budget = budgetRepository.findByIdAndUserId(
                id,
                user.getId()
        ).orElseThrow(BudgetNotFoundException::new);

        budgetRepository.delete(budget);

    }

}