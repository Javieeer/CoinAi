package com.coinai.api.common.exception;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException() {
        super("Invalid or expired token.");
    }

}