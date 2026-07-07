package com.coinai.api.auth.service.impl;

import com.coinai.api.auth.dto.request.LoginRequest;
import com.coinai.api.auth.dto.response.LoginResponse;
import com.coinai.api.auth.service.AuthService;
import com.coinai.api.common.exception.InvalidCredentialsException;
import com.coinai.api.user.entity.User;
import com.coinai.api.user.repository.UserRepository;
import com.coinai.api.security.JwtService;
import com.coinai.api.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtProperties.getAccessTokenExpirationInSeconds())
                .build();

    }

}