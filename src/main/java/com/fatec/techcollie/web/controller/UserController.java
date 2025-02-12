package com.fatec.techcollie.web.controller;

import com.fatec.techcollie.model.User;
import com.fatec.techcollie.service.UserService;
import com.fatec.techcollie.web.dto.user.UserCreateDto;
import com.fatec.techcollie.web.dto.user.UserResponseDto;
import com.fatec.techcollie.web.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> insert(@RequestBody @Valid UserCreateDto dto){
        User user = UserMapper.toUser(dto);
        userService.insert(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .buildAndExpand("{}", user.getId())
                .toUri();
        UserResponseDto response = UserMapper.toResponseDto(user);
        return ResponseEntity.created(uri).body(response);
    }
}
