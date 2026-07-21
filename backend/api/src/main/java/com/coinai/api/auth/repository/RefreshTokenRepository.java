package com.coinai.api.auth.repository;

import com.coinai.api.auth.entity.RefreshToken;
import com.coinai.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

}