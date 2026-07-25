package com.coinai.api.automation.service;

import com.coinai.api.automation.extraction.dto.FreeTextClassificationResponse;

public interface MovementClassificationService {

    FreeTextClassificationResponse classify(String text);

}