package com.coinai.api.movement.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.entity.Movement;

public interface MovementRepository extends JpaRepository<Movement, UUID> {

    List<Movement> findByUserIdOrderByMovementDateDesc(UUID userId);

    Optional<Movement> findByIdAndUserId(UUID id, UUID userId);

    List<Movement> findByUserIdAndMovementTypeOrderByMovementDateDesc(
        UUID userId,
        MovementType movementType
    );

    List<Movement> findByUserIdAndCategoryId(
        UUID userId,
        UUID categoryId
    );

    List<Movement> findByUserIdAndMovementDateBetween(
        UUID userId,
        LocalDateTime start,
        LocalDateTime end
    );

    List<Movement> findByUserIdAndCategoryIdAndMovementDateBetween(
            UUID userId,
            UUID categoryId,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Movement> findByUserIdAndCategoryIdAndMovementTypeAndMovementDateBetween(
            UUID userId,
            UUID categoryId,
            MovementType movementType,
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("""
        SELECT COALESCE(SUM(m.amount), 0)
        FROM Movement m
        WHERE m.user.id = :userId
        AND m.category.id = :categoryId
        AND m.movementType = :movementType
        AND m.movementDate BETWEEN :start AND :end
    """)
    BigDecimal sumAmountByUserAndCategoryAndTypeAndDateBetween(
            @Param("userId") UUID userId,
            @Param("categoryId") UUID categoryId,
            @Param("movementType") MovementType movementType,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT COALESCE(SUM(m.amount), 0)
        FROM Movement m
        WHERE m.account.id = :accountId
        AND m.movementType = :movementType
    """)
    BigDecimal sumAmountByAccountIdAndMovementType(
            @Param("accountId") UUID accountId,
            @Param("movementType") MovementType movementType
    );

    @Query("""
        SELECT COALESCE(SUM(m.amount), 0)
        FROM Movement m
        WHERE m.user.id = :userId
        AND m.movementType = :movementType
    """)
    BigDecimal sumAmountByUserAndMovementType(
            UUID userId,
            MovementType movementType
    );

    @Query("""
        SELECT
            m.category.id,
            m.category.name,
            COUNT(m)
        FROM Movement m
        WHERE m.user.id = :userId
        GROUP BY
            m.category.id,
            m.category.name
        ORDER BY COUNT(m) DESC
    """)
    List<Object[]> findTopCategories(UUID userId);

    @Query("""
        SELECT COALESCE(SUM(m.amount), 0)
        FROM Movement m
        WHERE m.user.id IN :userIds
        AND m.movementType = :movementType
    """)
    BigDecimal sumAmountByUserIdsAndMovementType(
            List<UUID> userIds,
            MovementType movementType
    );

    @Query("""
        SELECT
            m.category.id,
            m.category.name,
            COUNT(m)
        FROM Movement m
        WHERE m.user.id IN :userIds
        AND m.movementType = :movementType
        GROUP BY
            m.category.id,
            m.category.name
        ORDER BY COUNT(m) DESC
    """)
    List<Object[]> findTopCategoriesByUserIds(
            List<UUID> userIds,
            MovementType movementType
    );

    @Query("""
        SELECT COALESCE(SUM(m.amount), 0)
        FROM Movement m
        WHERE m.user.id = :userId
        AND m.movementType = :movementType
    """)
    BigDecimal sumAmountByUserIdAndMovementType(
            UUID userId,
            MovementType movementType
    );
    
}