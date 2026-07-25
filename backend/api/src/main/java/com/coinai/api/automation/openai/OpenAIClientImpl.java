package com.coinai.api.automation.openai;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.Response;

@Service
@RequiredArgsConstructor
public class OpenAIClientImpl implements AIClient {

    private final com.openai.client.OpenAIClient openAIClient;
    private final OpenAIProperties properties;

    @Override
    public String chat(String prompt) {

        ResponseCreateParams params = ResponseCreateParams.builder()
                .model(properties.getModel())
                .input(prompt)
                .build();

        Response response = openAIClient
                .responses()
                .create(params);

        System.out.println(response.output());

        return response.output().stream()
            .filter(item -> item.message().isPresent())
            .map(item -> item.message().get())
            .flatMap(message -> message.content().stream())
            .filter(content -> content.outputText().isPresent())
            .map(content -> content.outputText().get().text())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("OpenAI did not return any text."));

    }

}