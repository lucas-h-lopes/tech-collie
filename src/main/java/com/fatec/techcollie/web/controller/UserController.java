package com.fatec.techcollie.web.controller;

import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.UserRole;
import com.fatec.techcollie.service.AddressService;
import com.fatec.techcollie.service.UserService;
import com.fatec.techcollie.web.dto.address.AddressDTO;
import com.fatec.techcollie.web.dto.page.PageableDTO;
import com.fatec.techcollie.web.dto.user.UserAdditionalDTO;
import com.fatec.techcollie.web.dto.user.UserCreateDTO;
import com.fatec.techcollie.web.dto.user.UserPasswordDTO;
import com.fatec.techcollie.web.dto.user.UserResponseDTO;
import com.fatec.techcollie.web.mapper.AddressMapper;
import com.fatec.techcollie.web.mapper.PageableMapper;
import com.fatec.techcollie.web.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final AddressService addressService;

    public UserController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> insertCommon(@RequestBody @Valid UserCreateDTO dto) {
        return createUser(dto, UserRole.MINIMUM_ACCESS);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<UserResponseDTO> insertAdmin(@RequestBody @Valid UserCreateDTO dto) {
        return createUser(dto, UserRole.ADMIN);
    }

    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('MINIMUM_ACCESS') and #id == authentication.principal.id)")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<PageableDTO> findAll(@PageableDefault(sort = "name") Pageable pageable,
                                               @RequestParam(name = "firstName", required = false) String firstName,
                                               @RequestParam(name = "surname", required = false) String surname,
                                               @RequestParam(name = "username", required = false) String username) {
        return ResponseEntity.ok(PageableMapper.toDTO(userService.findAll(pageable, firstName, surname, username)));
    }

    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('MINIMUM_ACCESS') and #id == authentication.principal.id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS') and #userId == authentication.principal.id")
    @PatchMapping("/{userId}/addresses")
    public ResponseEntity<Void> updateAddress(@PathVariable Integer userId, @RequestBody @Valid AddressDTO dto) {
        addressService.update(userId, AddressMapper.toAddress(dto));
        return ResponseEntity.noContent()
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS') and #userId == authentication.principal.id")
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateAdditional(@PathVariable Integer userId, @RequestBody @Valid UserAdditionalDTO dto) {
        userService.updateAdditional(userId, UserMapper.toAdditionalUser(dto));
        return ResponseEntity.noContent()
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS') and #userId == authentication.principal.id")
    @PutMapping("/{userId}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable int userId, @RequestBody @Valid UserPasswordDTO dto) {
        userService.updatePassword(dto.currentPassword(), dto.newPassword(), dto.confirmationPassword(), userId);
        return ResponseEntity.noContent()
                .build();
    }

    private ResponseEntity<UserResponseDTO> createUser(UserCreateDTO dto, UserRole role) {
        User user = UserMapper.toUser(dto);
        user.setRole(role);
        userService.insert(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        UserResponseDTO response = UserMapper.toResponseDto(user);
        return ResponseEntity.created(uri).body(response);
    }
}
