package com.coinai.api.automation.learning.repository;

import com.coinai.api.automation.learning.entity.MerchantRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MerchantRuleRepository extends JpaRepository<MerchantRule, Long> {

    Optional<MerchantRule> findByUserIdAndRawMerchant(
            UUID userId,
            String rawMerchant
    );

}