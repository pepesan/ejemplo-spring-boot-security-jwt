package com.cursosdedesarrollo.ejemplospringbootsecurityjwt.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    @Value("${app.jwt-refresh-token-milliseconds}")
    public long jwtRefreshExpirationDate;

    // Clave secreta simétrica (HMAC)
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Generar access token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationDate);

        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key()) // ✅ versión moderna: el algoritmo se elige según la clave
                .compact();
    }

    // Generar refresh token
    public String generateRefreshToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtRefreshExpirationDate);

        return Jwts.builder()
                .subject(username)
                .claim("type", "refresh")
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key()) // ✅ igual
                .compact();
    }

    // Obtener username
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Validar token (con comprobación del algoritmo)
    public boolean validateToken(String token) {
        try {
            Jws<Claims> jwt = Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(token);

            // Validar que el header.alg empareje con el algoritmo esperado
            String alg = (String) jwt.getHeader().get("alg");
            if (alg == null || !alg.startsWith("HS")) { // HMAC esperado
                throw new SecurityException("Algoritmo inesperado: " + alg);
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token inválido o expirado: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Error de seguridad JWT: " + e.getMessage());
        }
        return false;
    }
}
