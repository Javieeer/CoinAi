package com.coinai.api.budget.repository;

import com.coinai.api.budget.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    Optional<Budget> findByIdAndUserId(
            UUID id,
            UUID userId
    );

    boolean existsByUserIdAndFamilyIdAndCategoryIdAndMonthAndYear(
            UUID userId,
            UUID familyId,
            UUID categoryId,
            Short month,
            Short year
    );

    boolean existsByUserIdAndFamilyIsNullAndCategoryIdAndMonthAndYear(
        UUID userId,
        UUID categoryId,
        Short month,
        Short year
    );

    List<Budget> findByUserIdAndMonthAndYear(
            UUID userId,
            Short month,
            Short year
    );

    List<Budget> findByFamilyIdAndMonthAndYear(
            UUID familyId,
            Short month,
            Short year
    );

}