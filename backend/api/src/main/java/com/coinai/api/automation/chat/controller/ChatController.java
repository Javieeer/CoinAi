package com.coinai.api.automation.chat.controller;

import com.coinai.api.automation.chat.dto.request.ChatRequest;
import com.coinai.api.automation.chat.dto.response.ChatResponse;
import com.coinai.api.automation.chat.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/automation/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatResponse chat(
            @Valid @RequestBody ChatRequest request
    ) {
        return chatService.chat(request);
    }

}