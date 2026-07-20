package com.coinai.api.google.state.service;

import com.coinai.api.google.state.entity.OAuthState;

import java.util.UUID;

public interface OAuthStateService {

    OAuthState create(UUID userId);

    OAuthState get(String state);

    void delete(String state);

}