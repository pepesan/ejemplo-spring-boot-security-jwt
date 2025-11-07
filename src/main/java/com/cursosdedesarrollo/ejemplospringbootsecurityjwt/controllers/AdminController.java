package com.cursosdedesarrollo.ejemplospringbootsecurityjwt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/query")
public class AdminController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Map<String, String>> helloAdmin() {
        return ResponseEntity.ok(Map.of("message", "Hello Admin"));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> helloUser() {
        return ResponseEntity.ok(Map.of("message", "Hello User"));
    }

    @GetMapping("/authz")
    public ResponseEntity<Map<String, String>> helloAuthUser() {
        return ResponseEntity.ok(Map.of("message", "Hello Authenticated User"));
    }

}
