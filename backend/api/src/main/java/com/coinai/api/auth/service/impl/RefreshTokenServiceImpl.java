package com.coinai.api.auth.service.impl;

import com.coinai.api.auth.entity.RefreshToken;
import com.coinai.api.auth.exception.InvalidTokenException;
import com.coinai.api.auth.repository.RefreshTokenRepository;
import com.coinai.api.auth.service.RefreshTokenService;
import com.coinai.api.config.JwtProperties;
import com.coinai.api.security.JwtService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    public RefreshToken create(User user) {

        repository.deleteByUser(user);

        String token = jwtService.generateRefreshToken(user.getEmail());

        RefreshToken refreshToken = RefreshToken.builder()
                .id(UUID.randomUUID())
                .user(user)
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(
                        LocalDateTime.now()
                                .plus(jwtProperties.getRefreshTokenExpiration())
                )
                .build();

        return repository.save(refreshToken);

    }

    @Override
    public RefreshToken validate(String token) {

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(InvalidTokenException::new);

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {

            repository.delete(refreshToken);

            throw new InvalidTokenException();

        }

        if (!jwtService.isTokenValid(token)) {
            throw new InvalidTokenException();
        }

        return refreshToken;

    }

    @Override
    public void revoke(User user) {

        repository.deleteByUser(user);

    }

}