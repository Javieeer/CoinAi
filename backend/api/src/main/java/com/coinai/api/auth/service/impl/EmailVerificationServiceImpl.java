package com.coinai.api.auth.service.impl;

import com.coinai.api.auth.entity.EmailVerificationToken;
import com.coinai.api.auth.exception.InvalidTokenException;
import com.coinai.api.auth.repository.EmailVerificationTokenRepository;
import com.coinai.api.auth.service.EmailVerificationService;
import com.coinai.api.email.service.EmailService;
import com.coinai.api.user.entity.User;
import com.coinai.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl
        implements EmailVerificationService {

    private final EmailVerificationTokenRepository repository;

    private final UserRepository userRepository;

    private final EmailService emailService;

    @Override
    public void createVerificationToken(User user) {

        repository.deleteByUser(user);

        EmailVerificationToken token =
                EmailVerificationToken.builder()
                        .user(user)
                        .token(UUID.randomUUID().toString())
                        .createdAt(LocalDateTime.now())
                        .expiresAt(
                                LocalDateTime.now().plusHours(24)
                        )
                        .build();

        repository.save(token);

        String link =
                "http://localhost:3000/verify-email?token="
                        + token.getToken();

        try {

            emailService.sendVerificationEmail(
                    user.getEmail(),
                    user.getFirstName(),
                    link
            );

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public void verify(String token) {

        EmailVerificationToken verificationToken =
                repository.findByToken(token)
                        .orElseThrow(
                                InvalidTokenException::new
                        );

        if (verificationToken.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            repository.delete(verificationToken);

            throw new InvalidTokenException();

        }

        User user = verificationToken.getUser();

        user.setEmailVerified(true);

        userRepository.save(user);

        repository.delete(verificationToken);

    }

    @Override
    public void resend(String email) {

        userRepository.findByEmail(email)
                .ifPresent(this::createVerificationToken);

    }

}