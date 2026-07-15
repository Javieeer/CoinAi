package com.coinai.api.automation.chat.service;

import com.coinai.api.automation.chat.dto.request.ChatRequest;
import com.coinai.api.automation.chat.dto.response.ChatResponse;

public interface ChatService {

    ChatResponse chat(ChatRequest request);

}