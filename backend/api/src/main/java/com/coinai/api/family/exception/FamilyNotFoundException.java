package com.coinai.api.family.exception;

public class FamilyNotFoundException extends RuntimeException {

    public FamilyNotFoundException() {
        super("Family not found.");
    }

}