package com.coinai.api.security;

import com.coinai.api.common.exception.InvalidTokenException;
import com.coinai.api.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateAccessToken(String email) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + jwtProperties.getAccessTokenExpiration().toMillis()
        );

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(String email) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + jwtProperties.getRefreshTokenExpiration().toMillis()
        );

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token) {

        return extractClaims(token).getSubject();

    }
    
    public boolean isTokenValid(String token) {

        try {

            extractClaims(token);
            return true;

        } catch (JwtException | IllegalArgumentException ex) {

            throw new InvalidTokenException();

        }

    }
    
    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}