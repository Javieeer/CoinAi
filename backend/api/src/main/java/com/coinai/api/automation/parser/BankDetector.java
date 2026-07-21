package com.coinai.api.automation.parser;

public interface BankDetector {

    BankType detect(

            String from,
            String subject,
            String snippet

    );

}