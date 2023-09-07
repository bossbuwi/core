package com.horizon.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Value("${supabase.secret}")
    private String secret;

    public boolean isTokenValid(String token) {
        // TODO: Create a logic to determine token validity based on time.
        return false;
    }

    public String getUsernameFromToken(String token) {
        return getSpecificClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getSpecificClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getSpecificClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
