package com.coinai.api.automation.provider.openai.service.impl;

import com.coinai.api.automation.provider.openai.service.OpenAIClientService;
import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.Response;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAIClientServiceImpl implements OpenAIClientService {

    private final OpenAIClient client;

    @Override
    public String chat(String prompt) {

        ResponseCreateParams params = ResponseCreateParams.builder()
                .model(ChatModel.GPT_5_MINI)
                .input(prompt)
                .build();

        Response response = client.responses().create(params);

        return response.output().stream()
            .filter(item -> item.message().isPresent())
            .map(item -> item.message().get())
            .flatMap(message -> message.content().stream())
            .filter(content -> content.outputText().isPresent())
            .map(content -> content.outputText().get().text())
            .findFirst()
            .orElse("");

    }

}