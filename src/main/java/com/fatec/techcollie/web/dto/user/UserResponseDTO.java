package com.fatec.techcollie.web.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fatec.techcollie.model.Address;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.Seniority;

import java.time.LocalDate;

public record UserResponseDTO(Integer id,
                              String username,
                              String name,
                              String surname,
                              String email,
                              @JsonInclude(JsonInclude.Include.NON_NULL)
                              LocalDate birthdate,
                              @JsonInclude(JsonInclude.Include.NON_NULL)
                              Address address,
                              @JsonInclude(JsonInclude.Include.NON_NULL)
                              Seniority seniority,
                              @JsonInclude(JsonInclude.Include.NON_NULL)
                              String areaInterest,
                              @JsonInclude(JsonInclude.Include.NON_NULL)
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
