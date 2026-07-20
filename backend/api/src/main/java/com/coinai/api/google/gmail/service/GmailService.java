package com.coinai.api.google.gmail.service;

import com.coinai.api.google.gmail.dto.GmailMessageResponse;

import java.util.List;

public interface GmailService {

    List<GmailMessageResponse> listMessages() throws Exception;
}