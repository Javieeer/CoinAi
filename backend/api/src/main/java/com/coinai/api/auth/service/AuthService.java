package com.coinai.api.auth.service;

import com.coinai.api.auth.dto.request.LoginRequest;
import com.coinai.api.auth.dto.request.RefreshTokenRequest;
import com.coinai.api.auth.dto.response.LoginResponse;
import com.coinai.api.auth.dto.response.RefreshTokenResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
    
    RefreshTokenResponse refresh(RefreshTokenRequest request);

    void logout();

}