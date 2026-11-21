package com.user.Service;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@ApplicationScoped
public class JwtService {

    private Key key;

    @PostConstruct
    public void init() {
        String secret = System.getenv("JWT_SECRET");

        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT_SECRET is missing");
        }

        key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );
    }

    public String generateToken(String email) {

        long expirationTime = 15 * 60 * 1000;

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith((javax.crypto.SecretKey) key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getEmail(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}