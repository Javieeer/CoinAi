package com.coinai.api.family.exception;

public class FamilyOwnerCannotLeaveException extends RuntimeException {

    public FamilyOwnerCannotLeaveException() {
        super("The family owner cannot leave the family.");
    }

}