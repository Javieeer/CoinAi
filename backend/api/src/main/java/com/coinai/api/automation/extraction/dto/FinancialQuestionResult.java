package com.coinai.api.automation.extraction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinancialQuestionResult {

    private String intent;

    private String category;

    private String period;

}