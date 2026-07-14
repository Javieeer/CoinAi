package com.coinai.api.goal.repository;

import com.coinai.api.goal.GoalStatus;
import com.coinai.api.goal.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {

    Optional<Goal> findByIdAndUserId(
            UUID id,
            UUID userId
    );

    List<Goal> findByUserIdOrderByTargetDateAsc(
            UUID userId
    );

    List<Goal> findByFamilyIdOrderByTargetDateAsc(
            UUID familyId
    );

    List<Goal> findByUserIdAndStatusOrderByTargetDateAsc(
            UUID userId,
            GoalStatus status
    );

    boolean existsByUserIdAndFamilyIdAndNameIgnoreCase(
            UUID userId,
            UUID familyId,
            String name
    );

    boolean existsByUserIdAndFamilyIsNullAndNameIgnoreCase(
            UUID userId,
            String name
    );

}