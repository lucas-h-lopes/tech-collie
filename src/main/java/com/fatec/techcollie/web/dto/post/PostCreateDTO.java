package com.fatec.techcollie.web.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateDTO(
        @NotBlank(message = "O conteúdo é obrigatório")
        @Size(max = 5000, message = "O conteúdo deve possuir ao máximo 5000 caracteres")
        String text
) {
}
