package com.coinai.api.google.gmail.cleaner;

import org.springframework.stereotype.Service;
import com.coinai.api.automation.parser.BankType;;

@Service
public class BancolombiaEmailBodyCleaner implements EmailBodyCleaner {

    @Override
    public BankType supports() {
        return BankType.BANCOLOMBIA;
    }

    @Override
    public String clean(String body) {

        if (body == null) {
            return "";
        }

        return body
                .replaceAll("\\[[^\\]]*\\]", "")
                .replace("\r", " ")
                .replace("\n", " ")
                .replaceAll("(?i)logo\\s+[A-Za-z]+", "")
                .replaceAll("(?i)icon\\d+", "")
                .replaceAll("(?i)footer-logo", "")
                .replaceAll("(?i)yellow-icon", "")
                .replaceAll("(?is)Tu seguridad es nuestra prioridad.*", "")
                .replaceAll("(?is)Vigilado Superintendencia.*", "")
                .replaceAll("\\s+", " ")
                .trim();
    }
}