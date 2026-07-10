package com.coinai.api.category.repository;

import com.coinai.api.category.entity.Category;
import com.coinai.api.movement.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByUserIdAndNameIgnoreCase(UUID userId, String name);

    List<Category> findByUserId(UUID userId);

    List<Category> findByUserIdAndMovementType(UUID userId, MovementType movementType);

    List<Category> findByIsDefaultTrue();

    List<Category> findByIsDefaultTrueAndMovementType(MovementType movementType);

    Optional<Category> findByIdAndUserId(UUID id, UUID userId);
}