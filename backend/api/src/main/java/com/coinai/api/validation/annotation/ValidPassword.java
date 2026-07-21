package com.coinai.api.validation.annotation;

import com.coinai.api.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default
            "La contraseña debe contener al menos una mayúscula, una minúscula, un número y un carácter especial.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}