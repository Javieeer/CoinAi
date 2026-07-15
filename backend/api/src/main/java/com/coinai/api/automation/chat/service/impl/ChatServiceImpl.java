package com.coinai.api.automation.chat.service.impl;

import com.coinai.api.automation.chat.dto.request.ChatRequest;
import com.coinai.api.automation.chat.dto.response.ChatResponse;
import com.coinai.api.automation.chat.service.ChatService;
import com.coinai.api.automation.provider.AIProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final AIProvider aiProvider;

    @Override
    public ChatResponse chat(ChatRequest request) {

        return ChatResponse.builder()
                .answer(aiProvider.chat(request.getMessage()))
                .build();

    }

}