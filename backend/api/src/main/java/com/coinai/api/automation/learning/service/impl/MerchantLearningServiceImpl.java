package com.coinai.api.automation.learning.service.impl;

import com.coinai.api.automation.learning.entity.MerchantRule;
import com.coinai.api.automation.learning.repository.MerchantRuleRepository;
import com.coinai.api.automation.learning.service.MerchantLearningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantLearningServiceImpl implements MerchantLearningService {

    private final MerchantRuleRepository repository;

    @Override
    public Optional<MerchantRule> findRule(
            UUID userId,
            String rawMerchant
    ) {

        return repository.findByUserIdAndRawMerchant(
                userId,
                rawMerchant
        );

    }

    @Override
    public MerchantRule saveRule(
            MerchantRule merchantRule
    ) {

        return repository.save(
                merchantRule
        );

    }

    @Override
    public void registerUsage(
            MerchantRule merchantRule
    ) {

        merchantRule.setTimesUsed(
                merchantRule.getTimesUsed() + 1
        );

        merchantRule.setLastUsed(
                LocalDateTime.now()
        );

        repository.save(
                merchantRule
        );

    }

    @Override
        public boolean exists(
                UUID userId,
                String rawMerchant
        ) {

        return repository
                .findByUserIdAndRawMerchant(userId, rawMerchant)
                .isPresent();

        }

}