package com.coinai.api.automation.extraction.service.impl;

import com.coinai.api.automation.extraction.dto.MovementExtractionResult;
import com.coinai.api.automation.extraction.service.ExtractionService;
import com.coinai.api.automation.openai.AIClient;
import com.coinai.api.automation.parser.AIResponseParser;
import com.coinai.api.automation.prompt.MovementExtractionPrompt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractionServiceImpl implements ExtractionService {

    private final AIClient aiClient;
    private final MovementExtractionPrompt promptBuilder;
    private final AIResponseParser parser;

    @Override
    public MovementExtractionResult extract(String email) {

        String prompt = promptBuilder.build(email);

        String response = aiClient.chat(prompt);

        return parser.parse(response);

    }

}

    