package com.coinai.api.family.exception;

public class InvalidInviteCodeException extends RuntimeException {

    public InvalidInviteCodeException() {
        super("Invalid invitation code.");
    }

}