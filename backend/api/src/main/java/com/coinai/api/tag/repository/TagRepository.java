package com.coinai.api.tag.repository;

import com.coinai.api.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    boolean existsByUserIdAndNameIgnoreCase(
            UUID userId,
            String name
    );

    Optional<Tag> findByIdAndUserId(
            UUID id,
            UUID userId
    );

    List<Tag> findByUserIdOrderByNameAsc(
            UUID userId
    );

}