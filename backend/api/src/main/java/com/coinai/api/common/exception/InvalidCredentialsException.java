package com.coinai.api.common.exception;

public class InvalidCredentialsException extends BusinessException {

    public InvalidCredentialsException() {
        super("Invalid email or password.");
    }

}