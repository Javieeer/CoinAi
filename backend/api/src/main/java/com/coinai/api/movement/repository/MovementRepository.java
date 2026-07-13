package com.coinai.api.movement.repository;

import com.coinai.api.movement.MovementType;
import com.coinai.api.movement.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
}