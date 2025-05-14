package com.fatec.techcollie.web.mapper;

import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.Seniority;
import com.fatec.techcollie.repository.projection.impl.UserProjectionImplementation;
import com.fatec.techcollie.service.exception.BadRequestException;
import com.fatec.techcollie.web.dto.user.UserAdditionalCreateDTO;
import com.fatec.techcollie.web.dto.user.UserCreateDTO;
import com.fatec.techcollie.web.dto.user.UserPostResponseDTO;
import com.fatec.techcollie.web.dto.user.UserResponseDTO;

import java.util.List;

public class UserMapper {

    private UserMapper() {
    }

    public static User toUser(UserCreateDTO dto) {
        return new User(dto);
    }

    public static UserPostResponseDTO toUserPostResponseDTO(User user){
        return new UserPostResponseDTO(
                user.getId(),user.getUsername(), user.getProfilePicUrl()
        );
    }

    public static User toAdditionalUser(UserAdditionalCreateDTO dto) {
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

    public static UserResponseDTO toResponseDto(User user) {
        return new UserResponseDTO(user);
    }

    public static List<UserResponseDTO> toResponseDtoList(List<User> users) {
        return users.stream().map(UserMapper::toResponseDto).toList();
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
