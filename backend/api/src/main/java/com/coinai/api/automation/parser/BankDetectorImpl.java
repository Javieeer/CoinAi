package com.coinai.api.automation.parser;

import org.springframework.stereotype.Component;

@Component
public class BankDetectorImpl implements BankDetector {

    @Override
    public BankType detect(

            String from,
            String subject,
            String snippet

    ) {

        String text = (

                from
                + " "
                + subject
                + " "
                + snippet

        ).toLowerCase();

        if (text.contains("bancolombia")) {

            return BankType.BANCOLOMBIA;

        }

        if (text.contains("davivienda")) {

            return BankType.DAVIVIENDA;

        }

        if (text.contains("pse")
                || text.contains("achcolombia")) {

            return BankType.PSE;

        }

        if (text.contains("nequi")) {

            return BankType.NEQUI;

        }

        if (text.contains("nubank")
                || text.contains("nu colombia")) {

            return BankType.NUBANK;

        }

        return BankType.UNKNOWN;

    }

}