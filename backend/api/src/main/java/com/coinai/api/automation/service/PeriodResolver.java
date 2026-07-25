package com.coinai.api.automation.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PeriodResolver {

    @Getter
    @AllArgsConstructor
    public static class DateRange {

        private LocalDateTime start;

        private LocalDateTime end;

    }

    public static DateRange resolve(String period) {

        LocalDate today = LocalDate.now();

        return switch (period) {

            case "TODAY" -> new DateRange(
                    today.atStartOfDay(),
                    today.atTime(23,59,59)
            );

            case "THIS_MONTH" -> new DateRange(
                    today.withDayOfMonth(1).atStartOfDay(),
                    today.withDayOfMonth(
                            today.lengthOfMonth()
                    ).atTime(23,59,59)
            );

            case "THIS_YEAR" -> new DateRange(
                    today.withDayOfYear(1).atStartOfDay(),
                    today.withMonth(12)
                            .withDayOfMonth(31)
                            .atTime(23,59,59)
            );

            default -> new DateRange(
                    LocalDate.of(2000,1,1).atStartOfDay(),
                    LocalDateTime.now()
            );

        };

    }

}