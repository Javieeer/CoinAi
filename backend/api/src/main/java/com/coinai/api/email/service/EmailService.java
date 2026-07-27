package com.coinai.api.email.service;

import com.resend.core.exception.ResendException;

public interface EmailService {

    void sendPasswordRecoveryEmail(
            String to,
            String name,
            String recoveryLink
    ) throws ResendException;

    void sendVerificationEmail(
            String to,
            String firstName,
            String verificationLink
    ) throws Exception;

}