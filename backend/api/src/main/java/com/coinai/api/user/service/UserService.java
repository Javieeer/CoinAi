package com.coinai.api.user.service;

import com.coinai.api.user.dto.request.RegisterRequest;
import com.coinai.api.user.dto.request.UpdateProfileRequest;
import com.coinai.api.user.dto.response.RegisterResponse;
import com.coinai.api.user.dto.response.UpdateProfileResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

    UpdateProfileResponse updateProfile(
            UpdateProfileRequest request
    );

}