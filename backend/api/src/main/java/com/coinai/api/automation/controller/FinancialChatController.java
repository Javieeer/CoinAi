package com.coinai.api.automation.controller;

import com.coinai.api.automation.extraction.dto.ChatRequest;
import com.coinai.api.automation.extraction.dto.ChatResponse;
import com.coinai.api.automation.service.FinancialChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/automation/chat")
@RequiredArgsConstructor
public class FinancialChatController {

    private final FinancialChatService service;

    @PostMapping
    public ChatResponse ask(
            @Valid @RequestBody ChatRequest request
    ) {

        return ChatResponse.builder()
                .answer(
                        service.ask(
                                request.getQuestion()
                        )
                )
                .build();

    }

}