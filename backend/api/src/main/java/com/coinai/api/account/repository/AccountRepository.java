package com.coinai.api.account.repository;

import com.coinai.api.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByUserIdAndNameIgnoreCase(UUID userId, String name);

    List<Account> findByUserIdAndArchivedFalseOrderByNameAsc(UUID userId);

    Optional<Account> findByIdAndUserId(UUID id, UUID userId);
}