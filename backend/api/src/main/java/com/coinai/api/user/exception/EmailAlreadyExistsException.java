package com.coinai.api.user.exception;

import com.coinai.api.common.exception.BusinessException;

public class EmailAlreadyExistsException extends BusinessException {

    public EmailAlreadyExistsException(String email) {
        super("The email '" + email + "' is already registered.");
    }

}