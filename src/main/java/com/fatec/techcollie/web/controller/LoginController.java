package com.fatec.techcollie.web.controller;

import com.fatec.techcollie.jwt.JwtUserDetailsService;
import com.fatec.techcollie.service.exception.BadRequestException;
import com.fatec.techcollie.service.exception.NotFoundException;
import com.fatec.techcollie.web.dto.user.UserAuthenticateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class LoginController {

    private final JwtUserDetailsService service;
    private final AuthenticationManager manager;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserAuthenticateDTO dto) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            manager.authenticate(token);
            return ResponseEntity.ok(
                    service.generateToken(dto.email())
            );
        } catch (AuthenticationException | NotFoundException e) {
            throw new BadRequestException("Credenciais inválidas");
        }
    }
}
