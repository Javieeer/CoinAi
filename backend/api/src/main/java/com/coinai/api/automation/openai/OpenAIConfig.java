package com.coinai.api.automation.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

    @Bean
    public OpenAIClient openAIClient(OpenAIProperties properties) {

        return OpenAIOkHttpClient.builder()
                .apiKey(properties.getApiKey())
                .build();

    }

}