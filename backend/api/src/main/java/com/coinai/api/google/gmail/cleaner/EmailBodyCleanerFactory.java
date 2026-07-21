package com.coinai.api.google.gmail.cleaner;

import com.coinai.api.automation.parser.BankType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailBodyCleanerFactory {

    private final List<EmailBodyCleaner> cleaners;

    public EmailBodyCleaner get(BankType bankType) {

        return cleaners.stream()
                .filter(c -> c.supports() == bankType)
                .findFirst()
                .orElseThrow();
    }
}