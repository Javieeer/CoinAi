package com.coinai.api.google.service;

import com.coinai.api.google.entity.GoogleCredential;
import com.coinai.api.google.repository.GoogleCredentialRepository;
import com.coinai.api.user.entity.User;
import com.coinai.api.user.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleCredentialServiceImpl
        implements GoogleCredentialService {

    private final GoogleCredentialRepository repository;
    private final UserRepository userRepository;

    @Override
    public void save(
            UUID userId,
            GoogleTokenResponse tokenResponse
    ) {

        User user = userRepository
                .findById(userId)
                .orElseThrow();

        GoogleCredential credential =
                repository
                        .findByUserId(userId)
                        .orElse(
                                GoogleCredential.builder()
                                        .user(user)
                                        .build()
                        );

        credential.setRefreshToken(
                tokenResponse.getRefreshToken()
        );

        credential.setAccessToken(
                tokenResponse.getAccessToken()
        );

        credential.setExpiresAt(
                LocalDateTime.now()
                        .plusSeconds(
                                tokenResponse.getExpiresInSeconds()
                        )
        );

        repository.save(credential);

    }

    @Override
    public GoogleCredential getByUserId(
            UUID userId
    ) {

        return repository.findByUserId(userId)
                .orElseThrow();

    }

}