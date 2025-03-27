package com.store.litflix.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtilPractice {
    private final SecretKey secret;

    @Value("${jwt.expiration-time}")
    private long expirationTime;

    public JwtUtilPractice(@Value("${jwt.secret}") String secretString) {
        if (secretString.length() < 32) {
            throw new IllegalArgumentException("the secret has to be at least"
                                               + " 32 characters long");
        }
        secret =
                Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userNameAsEmail) {
        return Jwts.builder()
                .subject(userNameAsEmail)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()))
                .signWith(secret)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);

            return !claimsJws.getPayload().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Expired or invalid JWT token");
        }
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }
}
