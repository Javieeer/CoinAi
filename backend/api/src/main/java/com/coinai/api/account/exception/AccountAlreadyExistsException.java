package com.coinai.api.account.exception;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException() {
        super("Ya existe una cuenta con ese nombre.");
    }

}