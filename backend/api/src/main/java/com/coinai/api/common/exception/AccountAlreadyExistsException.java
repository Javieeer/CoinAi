package com.coinai.api.common.exception;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException() {
        super("Ya existe una cuenta con ese nombre.");
    }

}