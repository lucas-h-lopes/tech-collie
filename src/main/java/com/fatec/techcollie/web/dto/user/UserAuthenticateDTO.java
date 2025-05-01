package com.fatec.techcollie.web.dto.user;

import com.fatec.techcollie.annotations.Password;
import jakarta.validation.constraints.Pattern;

public record UserAuthenticateDTO(
        @Pattern(regexp = "^[A-Za-z0-9._]{3,}@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "O email precisa seguir o seguinte padrão: 'lucas.hen@email.com'")
        String email,
        @Password(fieldName = "senha")
        String password
) {
}
