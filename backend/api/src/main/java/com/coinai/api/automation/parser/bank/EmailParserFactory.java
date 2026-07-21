package com.coinai.api.automation.parser.bank;

import com.coinai.api.automation.parser.BankType;

public interface EmailParserFactory {

    EmailParser get(BankType bankType);

}