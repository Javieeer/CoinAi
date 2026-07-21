package com.coinai.api.automation.parser.bank;

import com.coinai.api.automation.movement.dto.MovementDraft;
import com.coinai.api.automation.parser.BankType;

public interface EmailParser {

    boolean supports(BankType bankType);

    MovementDraft parse(String body);

}