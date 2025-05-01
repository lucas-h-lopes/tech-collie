package com.fatec.techcollie.jwt;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor
public class AuthenticatedUserProvider {

    public static String getAuthenticatedEmail(){
        Authentication authentication =
                SecurityContextHolder
                        .getContext().getAuthentication();
        return authentication == null ? null : authentication.getName();
    }
}
