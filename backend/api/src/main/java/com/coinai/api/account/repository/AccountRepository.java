package com.coinai.api.account.repository;

import com.coinai.api.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByUserIdAndNameIgnoreCase(UUID userId, String name);

}