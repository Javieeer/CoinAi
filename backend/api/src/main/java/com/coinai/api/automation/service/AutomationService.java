package com.coinai.api.automation.service;

import com.coinai.api.automation.extraction.dto.MovementExtractionResult;

import java.util.UUID;

public interface AutomationService {

    MovementExtractionResult processEmail(
            UUID userId,
            String email
    );

}