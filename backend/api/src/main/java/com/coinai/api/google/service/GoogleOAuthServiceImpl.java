package com.coinai.api.google.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GoogleOAuthServiceImpl implements GoogleOAuthService {

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Override
    public String buildAuthorizationUrl(String state) {

        String scope = URLEncoder.encode(
                "openid email profile https://www.googleapis.com/auth/gmail.readonly",
                StandardCharsets.UTF_8
        );

        return "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&scope=" + scope
                + "&access_type=offline"
                + "&prompt=consent"
                + "&state=" + state;

    }

    @Override
    public GoogleTokenResponse exchangeCode(
            String code
    ) throws IOException {

        GoogleAuthorizationCodeTokenRequest request =
                new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(),
                        GsonFactory.getDefaultInstance(),
                        clientId,
                        clientSecret,
                        code,
                        redirectUri
                );

        return request.execute();
    }

}