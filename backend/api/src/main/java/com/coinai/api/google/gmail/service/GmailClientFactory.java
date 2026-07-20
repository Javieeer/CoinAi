package com.coinai.api.google.gmail.service;

import com.coinai.api.google.entity.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@RequiredArgsConstructor
public class GmailClientFactory {

        @Value("${google.client-id}")
        private String clientId;

        @Value("${google.client-secret}")
        private String clientSecret;

    private final NetHttpTransport transport;
    private final GsonFactory gsonFactory;

    public Gmail create(GoogleCredential credential) {

        com.google.api.client.googleapis.auth.oauth2.GoogleCredential googleCredential =
                new Builder()
                        .setTransport(transport)
                        .setJsonFactory(gsonFactory)
                        .setClientSecrets(clientId, clientSecret)
                        .build();

        googleCredential.setAccessToken(
                credential.getAccessToken()
        );

        googleCredential.setRefreshToken(
                credential.getRefreshToken()
        );

        return new Gmail.Builder(
                transport,
                gsonFactory,
                googleCredential
        )
                .setApplicationName("CoinAI")
                .build();

    }

}