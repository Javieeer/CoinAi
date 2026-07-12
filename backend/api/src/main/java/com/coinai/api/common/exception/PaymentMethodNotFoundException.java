package com.coinai.api.common.exception;

public class PaymentMethodNotFoundException extends RuntimeException {
    
    public PaymentMethodNotFoundException() {
        super("Payment method not found.");
    }
}
