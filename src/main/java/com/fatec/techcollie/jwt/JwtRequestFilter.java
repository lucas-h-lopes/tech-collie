package com.fatec.techcollie.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${api.path.default}")
    private String baseUrl;
    @Value("${api.path.users}")
    private String usersUrl;
    @Value("${api.path.login}")
    private String loginUrl;

    @Autowired
    private JwtUserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String fUserUrl = baseUrl.concat(usersUrl);
        String fLoginUrl = baseUrl.concat(loginUrl);

        if (request.getRequestURI().equals(fUserUrl) && request.getMethod().equalsIgnoreCase("POST") || request.getRequestURI().equals(fLoginUrl)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            log.info("Token nulo ou não iniciado com Bearer ");
            filterChain.doFilter(request, response);
            return;
        }

        if (!service.isTokenValid(token)) {
            log.info("Sessão expirada ou token inválido");
            filterChain.doFilter(request, response);
            return;
        }

        String email = service.getSubject(token);

        submitToContext(email, request);
        filterChain.doFilter(request, response);
    }

    private void submitToContext(String email, HttpServletRequest request) {
        JwtUserDetails details = service.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken token =
                UsernamePasswordAuthenticationToken
                        .authenticated(details, null, details.getAuthorities());

        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext()
                .setAuthentication(token);
    }
}
