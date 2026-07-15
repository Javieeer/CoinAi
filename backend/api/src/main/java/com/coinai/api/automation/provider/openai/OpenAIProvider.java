package com.coinai.api.automation.provider.openai;

import com.coinai.api.automation.provider.AIProvider;
import com.coinai.api.automation.provider.openai.service.OpenAIClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAIProvider implements AIProvider {

    private final OpenAIClientService clientService;

    @Override
    public String chat(String prompt) {
        return clientService.chat(prompt);
    }

}