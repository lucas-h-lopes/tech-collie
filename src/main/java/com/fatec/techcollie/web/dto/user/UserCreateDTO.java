package com.fatec.techcollie.web.dto.user;

import com.fatec.techcollie.annotations.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(@NotBlank(message = "O nome de usuário é obrigatório")
                            @Size(min = 3, max = 15, message = "O nome de usuário precisa possuir de 3 a 15 caracteres")
                            String username,
                            @NotBlank(message = "O nome é obrigatório")
                            @Size(min = 3, max = 50, message = "O nome precisa possuir de 3 a 50 caracteres")
                            String name,
                            @NotBlank(message = "O sobrenome é obrigatório")
                            @Size(min = 3, max = 50, message = "O sobrenome precisa possuir de 3 a 50 caracteres")
                            String surname,
                            @NotBlank(message = "O email é obrigatório")
                            @Pattern(regexp = "^[A-Za-z0-9._]{3,}@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "O email precisa seguir o seguinte padrão: 'lucas.hen@email.com'")
                            String email,
                            @Password(fieldName = "senha")
                            String password) {
}
