package com.coinai.api.auth.service.impl;

import com.coinai.api.auth.entity.PasswordResetToken;
import com.coinai.api.auth.exception.InvalidTokenException;
import com.coinai.api.auth.repository.PasswordResetTokenRepository;
import com.coinai.api.auth.service.PasswordResetService;
import com.coinai.api.user.repository.UserRepository;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl
        implements PasswordResetService {

    private final PasswordResetTokenRepository repository;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createResetToken(String email) {

        userRepository.findByEmail(email)
                .ifPresent(user -> {

                    repository.deleteByUser(user);

                    PasswordResetToken resetToken =
                            PasswordResetToken.builder()
                                    .user(user)
                                    .token(UUID.randomUUID().toString())
                                    .createdAt(LocalDateTime.now())
                                    .expiresAt(
                                            LocalDateTime.now()
                                                    .plusMinutes(15)
                                    )
                                    .build();

                    repository.save(resetToken);

                });

    }

    @Override
    public PasswordResetToken validate(String token) {

        PasswordResetToken resetToken =
                repository.findByToken(token)
                        .orElseThrow(InvalidTokenException::new);

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {

            repository.delete(resetToken);

            throw new InvalidTokenException();

        }

        return resetToken;

    }

    @Override
    public void resetPassword(
            String token,
            String newPassword
    ) {

        PasswordResetToken resetToken =
                validate(token);

        User user =
                resetToken.getUser();

        user.setPasswordHash(
                passwordEncoder.encode(newPassword)
        );

        userRepository.save(user);

        repository.delete(resetToken);

    }

}