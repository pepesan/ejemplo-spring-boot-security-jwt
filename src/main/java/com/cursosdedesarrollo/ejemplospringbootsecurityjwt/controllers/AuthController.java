package com.cursosdedesarrollo.ejemplospringbootsecurityjwtv2.controllers;

import com.cursosdedesarrollo.ejemplospringbootsecurityjwtv2.dtos.JwtAuthResponse;
import com.cursosdedesarrollo.ejemplospringbootsecurityjwtv2.dtos.LoginDto;
import com.cursosdedesarrollo.ejemplospringbootsecurityjwtv2.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

}
