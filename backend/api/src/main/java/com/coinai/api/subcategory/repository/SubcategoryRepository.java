package com.coinai.api.subcategory.repository;

import com.coinai.api.subcategory.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface SubcategoryRepository extends JpaRepository<Subcategory, UUID> {

    Optional<Subcategory> findByIdAndUserId(
            UUID id,
            UUID userId
    );

    boolean existsByUserIdAndCategoryIdAndNameIgnoreCase(
            UUID userId,
            UUID categoryId,
            String name
    );

    List<Subcategory> findByUserIdAndArchivedFalseOrderByNameAsc(
            UUID userId
    );

}