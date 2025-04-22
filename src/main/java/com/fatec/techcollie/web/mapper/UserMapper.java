package com.fatec.techcollie.web.mapper;

import com.fatec.techcollie.model.User;
import com.fatec.techcollie.repository.projection.impl.UserProjectionImplementation;
import com.fatec.techcollie.web.dto.user.UserCreateDto;
import com.fatec.techcollie.web.dto.user.UserResponseDto;

import java.util.List;

public class UserMapper {

    private UserMapper(){}

    public static User toUser(UserCreateDto dto){
        return new User(dto);
    }

    public static UserResponseDto toResponseDto(User user){
        return new UserResponseDto(user);
    }

    public static List<UserResponseDto> toResponseDtoList(List<User> users){
        return users.stream().map(UserMapper::toResponseDto).toList();
    }

    public static UserProjectionImplementation toUserProjection(User user){
        return new UserProjectionImplementation(user);
    }
}
