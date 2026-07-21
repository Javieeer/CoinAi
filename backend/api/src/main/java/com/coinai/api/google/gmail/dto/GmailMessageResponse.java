package com.coinai.api.google.gmail.dto;

import com.coinai.api.automation.parser.BankType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GmailMessageResponse {

    private String id;

    private String from;

    private String subject;

    private String date;

    private String snippet;

    private BankType bankType;

    private String body;
}