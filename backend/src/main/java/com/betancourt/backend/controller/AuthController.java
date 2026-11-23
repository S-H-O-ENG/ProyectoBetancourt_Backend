package com.betancourt.backend.controller;

import com.betancourt.backend.dto.LoginRequest;
import com.betancourt.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Validar correo y contraseña encriptada
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Cargar detalles del colaborador activo
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // 3. Generar token JWT
        final String token = jwtService.generateToken(userDetails);

        Map<String, String> response = new HashMap<>();
        response.put("access_token", token);
        response.put("token_type", "Bearer");

        return ResponseEntity.ok(response);
    }
}