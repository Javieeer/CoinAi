package com.coinai.api.automation.email.repository;

import com.coinai.api.automation.email.entity.ProcessedEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessedEmailRepository
        extends JpaRepository<ProcessedEmail, UUID> {

    boolean existsByGmailMessageId(String gmailMessageId);

}