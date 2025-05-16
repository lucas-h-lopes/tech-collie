package com.fatec.techcollie.web.dto.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.Seniority;

@JsonPropertyOrder({"id", "username", "name", "surname", "email", "birthDate", "seniority", "interestArea", "profilePictureUrl", "address"})
public record UserSummaryDTO(Integer id,
                             String username,
                             String name,
                             String surname,
                             Seniority seniority,
                             String interestArea,
                             String profilePictureUrl) {

    public UserSummaryDTO(User user) {
        this(user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getSeniority(),
                user.getInterestArea(),
                user.getProfilePicUrl());
    }
}