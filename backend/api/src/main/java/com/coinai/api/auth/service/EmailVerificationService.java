package com.coinai.api.auth.service;

import com.coinai.api.user.entity.User;

public interface EmailVerificationService {

    void createVerificationToken(User user);

    void verify(String token);

    void resend(String email);

}