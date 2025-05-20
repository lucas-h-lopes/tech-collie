package com.fatec.techcollie.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticatedUserProvider {

    public static String getAuthenticatedEmail() {
        Authentication authentication = getAuthentication();
        System.out.println("Chamada a Authenticated Email: " + authentication.getName());
        return authentication instanceof AnonymousAuthenticationToken ? "Anonimo" : authentication.getName();
    }

    public static Integer getAuthenticatedId() {
        JwtUserDetails details = getDetails();
        System.out.println("Chamada a Authenticated Id: " + details.getId());
        return details.getId();
    }

    public static String getAuthenticatedUsername() {
        JwtUserDetails details = getDetails();
        System.out.println("Chamada a Authenticated Username: " + details.getUsername());
        return details.getUsername();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder
                .getContext().getAuthentication();
    }

    private static JwtUserDetails getDetails(){
        return (JwtUserDetails) getAuthentication().getPrincipal();
    }
}
