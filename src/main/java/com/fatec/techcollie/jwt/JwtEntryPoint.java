package com.fatec.techcollie.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;

public class JwtEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {


        String token = request.getHeader("Authorization");
        response.setStatus(401);
        response.setContentType("application/json");
        HashMap<String, String> message = new HashMap<>();
        if (token == null || token.isBlank()) {
            response.getWriter()
                    .write("Usuário não autenticado");
            return;
        }
        response.getWriter()
                .write("Sessão expirada ou token inválido");
    }
}
