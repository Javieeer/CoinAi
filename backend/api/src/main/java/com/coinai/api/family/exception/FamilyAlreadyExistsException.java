package com.coinai.api.family.exception;

public class FamilyAlreadyExistsException extends RuntimeException {

    public FamilyAlreadyExistsException() {
        super("You already belong to a family.");
    }

}