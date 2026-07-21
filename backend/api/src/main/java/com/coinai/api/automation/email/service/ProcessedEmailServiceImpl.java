package com.coinai.api.automation.email.service;

import com.coinai.api.automation.email.entity.ProcessedEmail;
import com.coinai.api.automation.email.repository.ProcessedEmailRepository;
import com.coinai.api.automation.parser.BankType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessedEmailServiceImpl
        implements ProcessedEmailService {

    private final ProcessedEmailRepository repository;

    @Override
    public boolean exists(String gmailMessageId) {

        return repository.existsByGmailMessageId(
                gmailMessageId
        );

    }

    @Override
    public void save(
            String gmailMessageId,
            BankType bankType
    ) {

        repository.save(
                ProcessedEmail.builder()
                        .id(UUID.randomUUID())
                        .gmailMessageId(gmailMessageId)
                        .bankType(bankType)
                        .processedAt(LocalDateTime.now())
                        .build()
        );

    }

}