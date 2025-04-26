package com.fatec.techcollie.annotations;

import com.fatec.techcollie.annotations.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "";
    String fieldName();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
