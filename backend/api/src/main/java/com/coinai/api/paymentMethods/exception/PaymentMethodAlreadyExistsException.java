package com.coinai.api.paymentMethods.exception;

public class PaymentMethodAlreadyExistsException extends RuntimeException {

    public PaymentMethodAlreadyExistsException() {
        super("Payment method already exists.");
    }

}