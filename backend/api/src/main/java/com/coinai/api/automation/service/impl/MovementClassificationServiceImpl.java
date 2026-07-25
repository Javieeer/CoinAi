package com.coinai.api.automation.service.impl;

import com.coinai.api.automation.extraction.dto.FreeTextClassificationResponse;
import com.coinai.api.automation.extraction.dto.MovementExtractionResult;
import com.coinai.api.automation.service.MovementClassificationService;
import com.coinai.api.automation.openai.AIClient;
import com.coinai.api.automation.parser.AIResponseParser;
import com.coinai.api.automation.prompt.FreeTextMovementPrompt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovementClassificationServiceImpl
        implements MovementClassificationService {

    private final FreeTextMovementPrompt prompt;

    private final AIClient aiClient;

    private final AIResponseParser parser;

    @Override
    public FreeTextClassificationResponse classify(String text) {

        String aiResponse = aiClient.chat(
                prompt.build(text)
        );

        MovementExtractionResult result =
                parser.parse(aiResponse);

        return FreeTextClassificationResponse.builder()
                .movementType(result.getMovementType())
                .amount(result.getAmount())
                .category(result.getCategory())
                .paymentMethod(result.getPaymentMethod())
                .merchant(result.getMerchant())
                .confidence(result.getConfidence())
                .build();

    }

}