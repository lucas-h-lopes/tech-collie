package com.fatec.techcollie.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {

    private final int minutes = 60;

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey generateSecret() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private Date generateExpDate(Date actual) {
        LocalDateTime dt = actual.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        dt = dt.plusMinutes(minutes);
        return Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public JwtToken generateToken(String email, String role) {
        Date exp = generateExpDate(new Date());
        String token = Jwts.builder()
                .expiration(exp)
                .issuedAt(new Date())
                .subject(email)
                .claim("role", role)
                .signWith(generateSecret())
                .header()
                .add("typ", "JWT")
                .and().compact();
        return new JwtToken(token);
    }

    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(generateSecret())
                    .build().parseSignedClaims(removeBearer(token))
                    .getPayload();
        } catch (JwtException e) {
            log.info("Falha na geração do token ", e);
        }
        return null;
    }

    public String getSubject(String token) {
        Claims claims = getClaimsFromToken(token);
        if(claims != null) {
            return claims.getSubject();
        }
        return "Claims está nulo";
    }

    public boolean isTokenValid(String token){
        try{
            Jwts
                    .parser()
                    .verifyWith(generateSecret())
                    .build()
                    .parseSignedClaims(removeBearer(token));
            return true;
        }catch(JwtException e){
            log.info("Falha na verificação token inválido ", e);
        }
        return false;
    }

    private String removeBearer(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring("Bearer ".length());
        }
        return token;
    }
}
