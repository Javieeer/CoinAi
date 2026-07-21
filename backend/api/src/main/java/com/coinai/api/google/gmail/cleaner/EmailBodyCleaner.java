package com.coinai.api.google.gmail.cleaner;

import com.coinai.api.automation.parser.BankType;

public interface EmailBodyCleaner {

    String clean(String body);

    BankType supports();
}