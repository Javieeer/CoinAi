package com.coinai.api.auth.repository;

import com.coinai.api.auth.entity.EmailVerificationToken;
import com.coinai.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationTokenRepository
        extends JpaRepository<EmailVerificationToken, UUID> {

    Optional<EmailVerificationToken> findByToken(String token);

    void deleteByUser(User user);

    boolean existsByToken(String token);

}