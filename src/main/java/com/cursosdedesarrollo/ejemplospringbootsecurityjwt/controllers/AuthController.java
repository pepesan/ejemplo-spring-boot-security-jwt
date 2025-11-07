package com.cursosdedesarrollo.ejemplospringbootsecurityjwt.controllers;

import com.cursosdedesarrollo.ejemplospringbootsecurityjwt.dtos.JwtAuthResponse;
import com.cursosdedesarrollo.ejemplospringbootsecurityjwt.dtos.LoginDto;
import com.cursosdedesarrollo.ejemplospringbootsecurityjwt.security.JwtTokenProvider;
import com.cursosdedesarrollo.ejemplospringbootsecurityjwt.services.AuthService;
import com.cursosdedesarrollo.ejemplospringbootsecurityjwt.services.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private JwtTokenProvider jwtTokenProvider;
    private CustomUserDetailsService customUserDetailsService;

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        String accessToken = authService.login(loginDto);

        // cargar UserDetails para generar refresh token y/o roles
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getUsernameOrEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        // crear cookie segura
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtTokenProvider == null ? 0 : jwtTokenProvider.jwtRefreshExpirationDate / 1000) // se usa valor en segundos
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", refreshCookie.toString());

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(accessToken);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    // Endpoint para refrescar access token usando la cookie refreshToken
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                                        HttpServletResponse response) {

        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = jwtTokenProvider.getUsername(refreshToken);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // generar nuevo access token y rotar refresh token
        String newAccessToken = jwtTokenProvider.generateToken(authentication);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        ResponseCookie newCookie = ResponseCookie.from("refreshToken", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtTokenProvider.jwtRefreshExpirationDate / 1000)
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", newCookie.toString());

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(newAccessToken);

        return ResponseEntity.ok(jwtAuthResponse);
    }

}
