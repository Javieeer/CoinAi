package com.coinai.api.automation.learning.service;

import com.coinai.api.automation.learning.dto.request.CreateMerchantRuleRequest;
import com.coinai.api.automation.learning.entity.MerchantRule;

import java.util.Optional;
import java.util.UUID;

public interface MerchantLearningService {

    Optional<MerchantRule> findRule(
        UUID userId,
        String rawMerchant
    );

    MerchantRule saveRule(
            MerchantRule merchantRule
    );

    void registerUsage(
            MerchantRule merchantRule
    );

    boolean exists(
            UUID userId,
            String rawMerchant
    );     

    MerchantRule learn(
        UUID userId,
        CreateMerchantRuleRequest request
    );

}