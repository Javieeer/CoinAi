package com.coinai.api.common.exception;

public class MovementNotFoundException extends RuntimeException {

    public MovementNotFoundException() {
        super("Movement not found.");
    }

}