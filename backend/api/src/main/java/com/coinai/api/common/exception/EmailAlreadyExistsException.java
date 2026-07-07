package com.coinai.api.common.exception;

public class EmailAlreadyExistsException extends BusinessException {

    public EmailAlreadyExistsException(String email) {
        super("The email '" + email + "' is already registered.");
    }

}