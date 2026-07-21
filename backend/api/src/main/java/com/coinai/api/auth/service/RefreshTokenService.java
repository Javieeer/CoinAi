package com.coinai.api.auth.service;

import com.coinai.api.auth.entity.RefreshToken;
import com.coinai.api.user.entity.User;

public interface RefreshTokenService {

    RefreshToken create(User user);

    RefreshToken validate(String token);

    void revoke(User user);

}