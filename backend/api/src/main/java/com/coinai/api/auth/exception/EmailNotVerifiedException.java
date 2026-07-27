package com.coinai.api.auth.exception;

public class EmailNotVerifiedException extends RuntimeException {

    public EmailNotVerifiedException() {

        super("Debes verificar tu correo antes de iniciar sesión.");

    }

}