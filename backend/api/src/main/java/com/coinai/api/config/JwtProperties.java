package com.coinai.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;

    private Duration accessTokenExpiration;
    
    private Duration refreshTokenExpiration;

    public long getAccessTokenExpirationInSeconds() {
        return accessTokenExpiration.toMillis() / 1000;
    }

    public long getRefreshTokenExpirationInSeconds() {
        return refreshTokenExpiration.toMillis() / 1000;
    }
}