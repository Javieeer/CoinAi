package com.coinai.api.automation.email.service;

import com.coinai.api.automation.parser.BankType;

public interface ProcessedEmailService {

    boolean exists(String gmailMessageId);

    void save(String gmailMessageId, BankType bankType);

}