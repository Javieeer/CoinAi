package com.coinai.api.google.service;

import com.coinai.api.google.entity.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import java.util.UUID;

public interface GoogleCredentialService {

    void save(
            UUID userId,
            GoogleTokenResponse tokenResponse
    );

    GoogleCredential getByUserId(UUID userId);

}