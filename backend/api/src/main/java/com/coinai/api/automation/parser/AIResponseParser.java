package com.coinai.api.automation.parser;

import com.coinai.api.automation.extraction.dto.MovementExtractionResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AIResponseParser {

    private final ObjectMapper objectMapper;

    public MovementExtractionResult parse(String response) {

        try {

            return objectMapper.readValue(
                    response,
                    MovementExtractionResult.class
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error parsing AI response",
                    e
            );

        }

    }

}