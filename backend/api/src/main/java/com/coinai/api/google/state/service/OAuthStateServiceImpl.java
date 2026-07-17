package com.coinai.api.google.state.service;

import com.coinai.api.google.state.entity.OAuthState;
import com.coinai.api.google.state.repository.OAuthStateRepository;
import com.coinai.api.user.entity.User;
import com.coinai.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthStateServiceImpl implements OAuthStateService {

    private final OAuthStateRepository repository;
    private final UserRepository userRepository;

    @Override
    public OAuthState create(UUID userId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow();

        OAuthState state = OAuthState.builder()
                .state(UUID.randomUUID().toString())
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .build();

        return repository.save(state);

    }

    @Override
    public OAuthState get(String state) {

        return repository.findByState(state)
                .orElseThrow();

    }

    @Override
    public void delete(String state) {

        repository.deleteByState(state);

    }

}