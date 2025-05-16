package com.fatec.techcollie.jwt;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class AuthenticatedUserProvider {

    @Autowired
    private EntityManager em;

    public String getAuthenticatedEmail() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken ? "Anonimo" : authentication.getName();
    }

    public Integer getAuthenticatedId() {
        String authenticatedEmail = getAuthenticatedEmail();
        Query query = em.createNativeQuery("select id from users u where u.email = ?");
        query.setParameter(1, authenticatedEmail);

        return (Integer) query.getSingleResult();
    }

    public String getAuthenticatedUsername(){
        String authenticatedEmail = getAuthenticatedEmail();
        Query query = em.createNativeQuery("select username from users u where u.email = ?");
        query.setParameter(1, authenticatedEmail);

        return (String) query.getSingleResult();
    }
}
