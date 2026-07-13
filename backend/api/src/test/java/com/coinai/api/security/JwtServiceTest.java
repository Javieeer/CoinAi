package com.coinai.api.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coinai.api.auth.exception.InvalidTokenException;
import com.coinai.api.config.JwtProperties;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {

        JwtProperties jwtProperties = new JwtProperties();

        jwtProperties.setSecret("MiClaveSuperSecretaQueTieneMasDe32Caracteres123");
        jwtProperties.setAccessTokenExpiration(Duration.ofHours(1));
        jwtProperties.setRefreshTokenExpiration(Duration.ofDays(7));

        jwtService = new JwtService(jwtProperties);

    }

    @Test
    void shouldGenerateAccessToken() {

        String token = jwtService.generateAccessToken("correo@coinai.com");

        assertNotNull(token);
        assertFalse(token.isBlank());

    }

    @Test
    void shouldExtractEmailFromToken() {

        String token = jwtService.generateAccessToken("javier@test.com");
        String email = jwtService.extractEmail(token);

        assertEquals("javier@test.com", email);

    }

    @Test
    void shouldValidateGeneratedToken() {

        String token = jwtService.generateAccessToken("javier@test.com");

        assertTrue(jwtService.isTokenValid(token));

    }

    @Test
    void shouldThrowExceptionForInvalidToken() {

        assertThrows(
                InvalidTokenException.class,
                () -> jwtService.isTokenValid("esto-no-es-un-token")
        );

    }

}