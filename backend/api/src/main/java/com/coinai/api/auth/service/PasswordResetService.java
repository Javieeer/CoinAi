package com.coinai.api.auth.service;

import com.coinai.api.auth.entity.PasswordResetToken;

public interface PasswordResetService {

    void createResetToken(String email);

    PasswordResetToken validate(String token);

    void resetPassword(
            String token,
            String newPassword
    );

}