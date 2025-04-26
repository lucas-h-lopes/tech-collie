package com.fatec.techcollie.web.dto.user;

import com.fatec.techcollie.annotations.Password;

public record UserPasswordDTO(
        @Password(fieldName = "senha atual")
        String currentPassword,
        @Password(fieldName = "nova senha")
        String newPassword,
        @Password(fieldName = "confirmação de senha")
        String confirmationPassword
) {
}
