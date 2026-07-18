package com.coinai.api.google.gmail.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GmailConfiguration {

    @Bean
    public NetHttpTransport httpTransport() throws Exception {
        return new NetHttpTransport();
    }

    @Bean
    public GsonFactory gsonFactory() {
        return GsonFactory.getDefaultInstance();
    }

}