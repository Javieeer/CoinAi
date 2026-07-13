package com.coinai.api.paymentMethods.exception;

public class PaymentMethodNotFoundException extends RuntimeException {
    
    public PaymentMethodNotFoundException() {
        super("Payment method not found.");
    }
}
