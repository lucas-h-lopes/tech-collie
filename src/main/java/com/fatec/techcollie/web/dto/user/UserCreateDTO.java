package com.fatec.techcollie.web.dto.user;

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
                            @NotBlank(message = "A senha é obrigatória")
                                @Size(min = 8, message = "A senha precisa possuir ao menos 8 caracteres")
                                @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                                        message = "A senha precisa possuir ao menos 8 caracteres, " +
                                                "incluindo uma letra maiúscula, um número, e um caractere especial")
                                String password) {
}
