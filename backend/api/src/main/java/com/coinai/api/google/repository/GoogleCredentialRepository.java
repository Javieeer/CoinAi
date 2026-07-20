package com.coinai.api.google.repository;

import com.coinai.api.google.entity.GoogleCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GoogleCredentialRepository
        extends JpaRepository<GoogleCredential, Long> {

    Optional<GoogleCredential> findByUserId(UUID userId);

}