package com.coinai.api.automation.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AIResponseParser {

    private final ObjectMapper objectMapper;

    public <T> T parse(
            String response,
            Class<T> clazz
    ) {

        try {

            return objectMapper.readValue(
                    response,
                    clazz
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error parsing AI response",
                    e
            );

        }

    }

}