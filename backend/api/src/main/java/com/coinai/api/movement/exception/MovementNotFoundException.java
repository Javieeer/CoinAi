package com.coinai.api.movement.exception;

public class MovementNotFoundException extends RuntimeException {

    public MovementNotFoundException() {
        super("Movement not found.");
    }

}