package com.coinai.api.validation.validator;

import com.coinai.api.validation.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator
        implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(
            String password,
            ConstraintValidatorContext context
    ) {

        if (password == null) {
            return false;
        }

        boolean hasUppercase =
                password.matches(".*[A-Z].*");

        boolean hasLowercase =
                password.matches(".*[a-z].*");

        boolean hasNumber =
                password.matches(".*\\d.*");

        boolean hasSpecialCharacter =
                password.matches(".*[^A-Za-z0-9].*");

        return hasUppercase
                && hasLowercase
                && hasNumber
                && hasSpecialCharacter;

    }

}