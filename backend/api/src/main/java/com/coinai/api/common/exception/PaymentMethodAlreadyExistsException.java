package com.coinai.api.common.exception;

public class PaymentMethodAlreadyExistsException extends RuntimeException {

    public PaymentMethodAlreadyExistsException() {
        super("Payment method already exists.");
    }

}