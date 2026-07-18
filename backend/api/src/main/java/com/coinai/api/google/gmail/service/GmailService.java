package com.coinai.api.google.gmail.service;

import com.google.api.services.gmail.model.Message;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface GmailService {

    List<String> listMessages() throws Exception;
}