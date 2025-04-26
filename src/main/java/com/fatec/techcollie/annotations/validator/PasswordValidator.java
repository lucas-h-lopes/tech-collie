package com.fatec.techcollie.annotations.validator;

import com.fatec.techcollie.annotations.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private String fieldName;

    public void initialize(Password password){
        this.fieldName = password.fieldName();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (password.isBlank()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(String.format("A %s é obrigatória", fieldName))
                    .addConstraintViolation();
            return false;
        }

        if (password.length() < 8) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(String.format("A %s precisa conter ao menos 8 caracteres", fieldName))
                    .addConstraintViolation();
            return false;
        }

        if (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("A " + fieldName + " precisa incluir ao menos uma letra maiúscula, um número e um caractere especial")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
