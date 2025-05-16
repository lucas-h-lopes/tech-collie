package com.fatec.techcollie.web.controller;

import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.UserRole;
import com.fatec.techcollie.repository.projection.PostProjection;
import com.fatec.techcollie.service.AddressService;
import com.fatec.techcollie.service.PostService;
import com.fatec.techcollie.service.UserService;
import com.fatec.techcollie.web.dto.address.AddressDTO;
import com.fatec.techcollie.web.dto.page.PageableDTO;
import com.fatec.techcollie.web.dto.page.WithUserPageableDTO;
import com.fatec.techcollie.web.dto.user.*;
import com.fatec.techcollie.web.mapper.AddressMapper;
import com.fatec.techcollie.web.mapper.PageableMapper;
import com.fatec.techcollie.web.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    private final PostService postService;

    public UserController(UserService userService, AddressService addressService, PostService postService) {
        this.userService = userService;
        this.addressService = addressService;
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<UserDetailsDTO> insertCommon(@RequestBody @Valid UserCreateDTO dto) {
        return createUser(dto, UserRole.MINIMUM_ACCESS);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<UserDetailsDTO> insertAdmin(@RequestBody @Valid UserCreateDTO dto) {
        return createUser(dto, UserRole.ADMIN);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MINIMUM_ACCESS')")
    @GetMapping("/{identifier}")
    public ResponseEntity<?> getByIdentifier(@PathVariable String identifier) {
        return ResponseEntity.ok(userService.getUserBasedOnIdentifier(identifier));
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS')")
    @PatchMapping("/me/addresses")
    public ResponseEntity<Void> updateAddress(@RequestBody @Valid AddressDTO dto) {
        addressService.update(AddressMapper.toAddress(dto));
        return ResponseEntity.noContent()
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS')")
    @PatchMapping("/me")
    public ResponseEntity<Void> updateAdditional(@RequestBody @Valid UserAdditionalDTO dto) {
        userService.updateAdditional(UserMapper.toAdditionalUser(dto));
        return ResponseEntity.noContent()
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS')")
    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserPasswordDTO dto) {
        userService.updatePassword(dto.currentPassword(), dto.newPassword(), dto.confirmationPassword());
        return ResponseEntity.noContent()
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS')")
    @GetMapping("/{id}/posts")
    public ResponseEntity<WithUserPageableDTO> getAllPosts(@PathVariable Integer id, Pageable pageable) {
        User user = userService.getById(id);
        UserBasicDTO simplifiedUser = UserMapper.toBasicUser(user);

        Page<PostProjection> page = (postService.getByUser(user, pageable));

        WithUserPageableDTO pageableDto = PageableMapper.toUserDTO(page, simplifiedUser);

        return ResponseEntity.ok(pageableDto);
    }

    private ResponseEntity<UserDetailsDTO> createUser(UserCreateDTO dto, UserRole role) {
        User user = UserMapper.toUser(dto);
        user.setRole(role);
        userService.insert(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        UserDetailsDTO response = UserMapper.toUserDetailsDto(user);
        return ResponseEntity.created(uri).body(response);
    }
}
