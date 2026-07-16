package com.coinai.api.automation.learning.service.impl;

import com.coinai.api.automation.learning.dto.request.CreateMerchantRuleRequest;
import com.coinai.api.automation.learning.entity.MerchantRule;
import com.coinai.api.automation.learning.repository.MerchantRuleRepository;
import com.coinai.api.automation.learning.service.MerchantLearningService;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.category.entity.Category;
import com.coinai.api.user.repository.UserRepository;
import com.coinai.api.user.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantLearningServiceImpl implements MerchantLearningService {

    private final MerchantRuleRepository repository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
        public Optional<MerchantRule> findRule(
                UUID userId,
                String rawMerchant
        ) {

                String normalizedMerchant =
                        rawMerchant.trim().toUpperCase();

                return repository.findByUserIdAndNormalizedMerchant(
                        userId,
                        normalizedMerchant
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
                .findByUserIdAndNormalizedMerchant(userId, rawMerchant)
                .isPresent();

        }

    @Override
        public MerchantRule learn(
                UUID userId,
                CreateMerchantRuleRequest request
        ) {

                User user = userRepository.findById(userId)
                        .orElseThrow();

                Category category = categoryRepository.findById(request.getCategoryId())
                        .orElseThrow();

                MerchantRule rule = MerchantRule.builder()
                        .user(user)
                        .rawMerchant(request.getRawMerchant())
                        .normalizedMerchant(request.getNormalizedMerchant())
                        .category(category)
                        .confirmedByUser(true)
                        .timesUsed(1)
                        .build();

                return repository.save(rule);

        }

}