package com.coinai.api.user.service;

import com.coinai.api.user.dto.request.RegisterRequest;
import com.coinai.api.user.dto.response.RegisterResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

}