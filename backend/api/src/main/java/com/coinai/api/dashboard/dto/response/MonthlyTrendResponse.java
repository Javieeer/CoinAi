package com.coinai.api.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTrendResponse {

    private Integer month;

    private Integer year;

    private BigDecimal income;

    private BigDecimal expense;

}