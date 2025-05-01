package com.fatec.techcollie.jwt;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private final com.fatec.techcollie.model.User user;

    public JwtUserDetails(com.fatec.techcollie.model.User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public Integer getId() {
        return this.user.getId();
    }

    public String getRole() {
        return this.user.getRole().name();
    }

}
