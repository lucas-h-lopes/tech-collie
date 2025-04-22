package com.fatec.techcollie.web.controller;

import com.fatec.techcollie.model.User;
import com.fatec.techcollie.service.UserService;
import com.fatec.techcollie.web.dto.page.PageableDTO;
import com.fatec.techcollie.web.dto.user.UserCreateDto;
import com.fatec.techcollie.web.dto.user.UserResponseDto;
import com.fatec.techcollie.web.mapper.PageableMapper;
import com.fatec.techcollie.web.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        UserResponseDto response = UserMapper.toResponseDto(user);
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Integer id){
        User user = userService.getById(id);
        return ResponseEntity.ok(new UserResponseDto(user));
    }

    @GetMapping
    public ResponseEntity<PageableDTO> findAll(@PageableDefault(sort = "name") Pageable pageable,
                                               @RequestParam(name = "firstName", required = false) String firstName,
                                               @RequestParam(name = "surname", required = false) String surname,
                                               @RequestParam(name = "username", required = false) String username){
        return ResponseEntity.ok(PageableMapper.toDTO(userService.findAll(pageable, firstName, surname, username)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        userService.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }
}
