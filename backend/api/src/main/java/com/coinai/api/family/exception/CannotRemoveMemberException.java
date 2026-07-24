package com.coinai.api.family.exception;

public class CannotRemoveMemberException extends RuntimeException {

    public CannotRemoveMemberException() {
        super("You cannot remove this family member.");
    }

}