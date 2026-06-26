package com.babu.spring_boot_demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService
{
    private static final String SECRET =
            "mySuperSecretKeyForMailApplication123456789";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes());

    public String generateToken(
            String email)
    {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000))
                .signWith(key)
                .compact();
    }

    public String extractEmail(
            String token)
    {
        Claims claims =
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

        return claims.getSubject();
    }

    public String extractToken(
            HttpServletRequest request)
    {
        String authHeader =
                request.getHeader("Authorization");

        if(authHeader == null ||
                !authHeader.startsWith("Bearer "))
        {
            throw new RuntimeException(
                    "Missing token");
        }

        return authHeader.substring(7);
    }

    public String extractEmailFromRequest(
            HttpServletRequest request)
    {
        String token =
                extractToken(request);

        return extractEmail(token);
    }

}