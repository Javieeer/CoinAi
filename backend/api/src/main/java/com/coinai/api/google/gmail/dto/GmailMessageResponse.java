package com.coinai.api.google.gmail.dto;

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

}