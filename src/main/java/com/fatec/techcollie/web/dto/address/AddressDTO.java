package com.fatec.techcollie.web.dto.address;

import jakarta.validation.constraints.Size;

public record AddressDTO(
        @Size(min = 5, max = 200, message = "A rua deve possuir de 5 a 200 caracteres")
        String street,
        @Size(min = 5, max = 200, message = "O bairro deve possuir de 5 a 200 caracteres")
        String district,
        @Size(min = 1, max = 10, message = "O número deve possuir de 1 a 10 caracteres")
        String number,
        @Size(min = 3, max = 100, message = "A cidade deve possuir de 3 a 100 caracteres")
        String city,
        @Size(min = 5, max = 50, message = "O estado deve possuir de 5 a 50 caracteres")
        String state,
        @Size(min = 5, max = 100, message = "O país deve possuir de 5 a 100 caracteres")
        String country
) {
}
