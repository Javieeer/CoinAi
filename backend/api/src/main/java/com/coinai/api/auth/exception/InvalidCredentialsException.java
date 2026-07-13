package com.coinai.api.auth.exception;

import com.coinai.api.common.exception.BusinessException;

public class InvalidCredentialsException extends BusinessException {

    public InvalidCredentialsException() {
        super("Invalid email or password.");
    }

}