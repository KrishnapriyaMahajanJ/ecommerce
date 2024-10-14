package com.scb.demo.ecommerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;


@Service
public class JwtService {

    private final String SECRET_KEY = "n2/w5mz/3LXbRmYhNGci6+X2jA370iUE9MSfo+iy6yUFbq9JEumkRfgYaWd1UoFSc+fJeznU9cyxmLUkioo2fBZ7Hj+YBLlh7mn+yu4cc9I1PPsxF16d4fLsX4mDqjQTopRBsUwowQ6wOtbi0decErKydoINcAZ4ab5IK7r2pNVyfhBUSs59+hCzx6InyPmtFx8VSwbZ7uHH5p11y6zIdRL7ONbf4D9SGENboIKq2D2xeqJFVdA/ng70uIr3wjr4qBJcJqN8kzG6tePjszkoYu0Fgv9kta6hdO6OZtvI1Bw6LrVCKr6QZcEvdBbjku0DWSzmTM9L/D4RP2mQdPRbRQ==";

    private Key getSigningKey() {
        // Use a Key object instead of a raw string for the signing key
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder() // Use parserBuilder() to create a JwtParser
                .setSigningKey(getSigningKey()) // Provide the signing key
                .build() // Build the parser
                .parseClaimsJws(token) // Parse the JWT token
                .getBody();                    // Get the body (claims)
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && isTokenActive(token);
    }

    public boolean isTokenActive(String token) {
        return !extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }


    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .claim("authorities", userDetails.getAuthorities())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        return isTokenActive(token);
    }
}
