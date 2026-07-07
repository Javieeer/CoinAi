package com.coinai.api.user.service.impl;

import com.coinai.api.common.exception.EmailAlreadyExistsException;
import com.coinai.api.user.dto.request.RegisterRequest;
import com.coinai.api.user.dto.response.RegisterResponse;
import com.coinai.api.user.entity.User;
import com.coinai.api.user.mapper.UserMapper;
import com.coinai.api.user.repository.UserRepository;
import com.coinai.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = mapper.toEntity(request);

        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        repository.save(user);

        return mapper.toResponse(user);

    }

}