package com.fatec.techcollie.web.dto.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fatec.techcollie.model.Address;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.Seniority;

import java.time.LocalDate;

@JsonPropertyOrder({"id", "username", "name", "surname", "email", "birthDate", "seniority", "interestArea", "profileUrl", "address"})
public record UserResponseDTO(Integer id,
                              String username,
                              String name,
                              String surname,
                              String email,
                              LocalDate birthDate,
                              Address address,
                              Seniority seniority,
                              String interestArea,
                              String profileUrl) {

    public UserResponseDTO(User user){
        this(user.getId(),
                "@"+user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getBirthDate(),
                user.getAddress(),
                user.getSeniority(),
                user.getInterestArea(),
                user.getProfilePicUrl());
    }
}
