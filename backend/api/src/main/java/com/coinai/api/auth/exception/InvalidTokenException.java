package com.coinai.api.auth.exception;

import com.coinai.api.common.exception.BusinessException;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException() {
        super("Invalid or expired token.");
    }

}