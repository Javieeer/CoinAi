package com.coinai.api.auth.service;

import com.coinai.api.auth.dto.request.LoginRequest;
import com.coinai.api.auth.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}