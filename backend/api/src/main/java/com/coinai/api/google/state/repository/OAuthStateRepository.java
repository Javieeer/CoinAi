package com.coinai.api.google.state.repository;

import com.coinai.api.google.state.entity.OAuthState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthStateRepository
        extends JpaRepository<OAuthState, Long> {

    Optional<OAuthState> findByState(String state);

    void deleteByState(String state);

}