package com.fatec.techcollie.config;

import com.fatec.techcollie.jwt.JwtEntryPoint;
import com.fatec.techcollie.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${api.path.default}")
    private String baseUrl;

    @Value("${api.path.users}")
    private String userResource;

    @Value("${api.path.login}")
    private String loginResource;

    @Bean
    public SecurityFilterChain configureFilterChain(HttpSecurity security) throws Exception {
        return security
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(
                                antMatcher(HttpMethod.POST, baseUrl + userResource),
                                antMatcher(HttpMethod.POST, baseUrl + loginResource)
                        ).permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(getFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(x -> x.authenticationEntryPoint(new JwtEntryPoint()))
                .build();
    }

    @Bean
    public JwtRequestFilter getFilter() {
        return new JwtRequestFilter();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
