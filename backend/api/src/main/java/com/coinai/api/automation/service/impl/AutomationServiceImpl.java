package com.coinai.api.automation.service.impl;

import com.coinai.api.automation.extraction.dto.MovementExtractionResult;
import com.coinai.api.automation.extraction.service.ExtractionService;
import com.coinai.api.automation.learning.service.MerchantLearningService;
import com.coinai.api.automation.service.AutomationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutomationServiceImpl implements AutomationService {

    private final ExtractionService extractionService;
    private final MerchantLearningService merchantLearningService;

    
        @Override
        public MovementExtractionResult processEmail(
                UUID userId,
                String email
        ) {

                MovementExtractionResult result =
                        extractionService.extract(email);

                merchantLearningService
                        .findRule(userId, result.getRawMerchant())
                        .ifPresent(rule -> {

                                result.setCategory(
                                        rule.getCategory().getName()
                                );

                                result.setNeedsUserInput(false);

                                merchantLearningService.registerUsage(rule);

                        });

                return result;

        }

}