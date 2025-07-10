package com.example.social_media_app.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import com.example.social_media_app.model.response.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
    private static SecretKey SECRET_KEY = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return Jwts.builder()
                .issuer("Social Media App")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JwtConstant.EXPIRATION_TIME))
                .claim("email", userDetails.getUsername())
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String getEmailFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("email", String.class);
    }
}