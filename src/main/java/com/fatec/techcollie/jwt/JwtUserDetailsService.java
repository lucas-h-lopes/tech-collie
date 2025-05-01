package com.fatec.techcollie.jwt;

import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.UserRole;
import com.fatec.techcollie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final JwtUtils utils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByEmail(username);
        return new JwtUserDetails(user);
    }

    public JwtToken generateToken(String username) {
        UserRole role = userService.getByEmail(username).getRole();
        return utils.generateToken(username, role.name());
    }

    public boolean isTokenValid(String token){
        return utils.isTokenValid(token);
    }

    public String getSubject(String token){
        return utils.getSubject(token);
    }
}
