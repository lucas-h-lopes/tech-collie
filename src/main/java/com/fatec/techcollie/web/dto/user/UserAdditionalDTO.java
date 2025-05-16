package com.fatec.techcollie.web.dto.user;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserAdditionalCreateDTO(
        @Size(min = 3, max = 50, message = "O nome precisa possuir de 3 a 50 caracteres")
        String name,
        @Size(min = 3, max = 50, message = "O sobrenome precisa possuir de 3 a 50 caracteres")
        String surname,
        LocalDate birthDate,
        String seniority,
        @Size(min = 3, max = 50, message = "A área de interesse precisa possuir de 3 a 50 caracteres")
        String interestArea,
        String pictureUrl
) {
}
