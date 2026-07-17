package com.coinai.api.google.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import java.io.IOException;


public interface GoogleOAuthService {

    String buildAuthorizationUrl();

    GoogleTokenResponse exchangeCode(
            String code
    ) throws IOException;
    
}