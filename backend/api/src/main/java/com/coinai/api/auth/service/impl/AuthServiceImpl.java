package com.coinai.api.auth.service.impl;

import com.coinai.api.auth.dto.request.LoginRequest;
import com.coinai.api.auth.dto.response.LoginResponse;
import com.coinai.api.auth.service.AuthService;
import com.coinai.api.common.exception.InvalidCredentialsException;
import com.coinai.api.user.entity.User;
import com.coinai.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return LoginResponse.builder()
                .accessToken("temporary-token")
                .refreshToken("temporary-refresh-token")
                .tokenType("Bearer")
                .expiresIn(3600L)
                .build();

    }

}