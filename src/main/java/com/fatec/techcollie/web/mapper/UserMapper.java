package com.fatec.techcollie.web.mapper;

import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.Seniority;
import com.fatec.techcollie.repository.projection.impl.UserProjectionImplementation;
import com.fatec.techcollie.service.exception.BadRequestException;

import com.fatec.techcollie.web.dto.user.*;

public class UserMapper {

    private UserMapper() {
    }

    public static User toUser(UserCreateDTO dto) {
        return new User(dto);
    }

    public static UserBasicDTO toBasicUser(User user){
        return new UserBasicDTO(
                user.getId(),user.getUsername(), user.getProfilePicUrl()
        );
    }

    public static UserSummaryDTO toUserSummaryDto(User user){
        return new UserSummaryDTO(user);
    }

    public static User toAdditionalUser(UserAdditionalDTO dto) {
        User user = new User();
        if (validateSeniority(dto.seniority())) {
            user.setSeniority(Seniority.valueOf(dto.seniority().toUpperCase()));
        }
        user.setInterestArea(dto.interestArea());
        user.setBirthDate(dto.birthDate());
        user.setProfilePicUrl(dto.pictureUrl());
        user.setName(dto.name());
        user.setSurname(dto.surname());

        return user;
    }

    public static UserDetailsDTO toUserDetailsDto(User user) {
        return new UserDetailsDTO(user);
    }

    public static UserProjectionImplementation toUserProjection(User user) {
        return new UserProjectionImplementation(user);
    }

    private static boolean validateSeniority(String seniority) {
        String validSeniority = "INTERN, JUNIOR, MID_LEVEL, SENIOR, SPECIALIST";
        try {
            if (seniority != null && !seniority.isBlank()) {
                Seniority.valueOf(seniority.toUpperCase());
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new BadRequestException("Senioridade informada é inválida. Permitidos: " + validSeniority);
        }
    }
}
