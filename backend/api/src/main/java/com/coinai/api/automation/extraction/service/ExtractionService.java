package com.coinai.api.automation.extraction.service;

import com.coinai.api.automation.extraction.dto.MovementExtractionResult;

public interface ExtractionService {

    MovementExtractionResult extract(String email);

}