package com.coinai.api.automation.learning.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantRuleResponse {

    private Long id;

    private String rawMerchant;

    private String normalizedMerchant;

    private String category;

    private Integer timesUsed;

    private Boolean confirmedByUser;

}