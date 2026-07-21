package com.coinai.api.google.gmail.provider;

import com.coinai.api.automation.parser.BankType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailProvider {

    BANCOLOMBIA(
            "an.notificacionesbancolombia.com",
            BankType.BANCOLOMBIA,
            1
    ),

    DAVIVIENDA(
            "davivienda.com",
            BankType.DAVIVIENDA,
            1
    ),

    PSE(
            "achcolombia.com.co",
            BankType.PSE,
            2
    );

    private final String domain;

    private final BankType bankType;

    private final int priority;

}