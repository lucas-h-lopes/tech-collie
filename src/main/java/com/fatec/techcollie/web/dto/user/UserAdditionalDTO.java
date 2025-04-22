package com.fatec.techcollie.web.dto.user;

import java.time.LocalDate;

public record UserAdditionalDTO(
        LocalDate birthDate,
        String seniority,
        String interestArea,
        String pictureUrl
) {
}
