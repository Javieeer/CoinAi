package com.coinai.api.automation.parser.bank;

import com.coinai.api.automation.parser.BankType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailParserFactoryImpl implements EmailParserFactory {

    private final List<EmailParser> parsers;

    @Override
    public EmailParser get(BankType bankType) {

        return parsers.stream()
                .filter(parser -> parser.supports(bankType))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No parser found for bank: " + bankType
                        )
                );

    }

}