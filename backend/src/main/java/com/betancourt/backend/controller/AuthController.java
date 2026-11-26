package com.betancourt.backend.controller;

import com.betancourt.backend.dto.*;
import com.betancourt.backend.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST del módulo de autenticación.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/login
     *
     * Valida las credenciales y devuelve el JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
            authService.login(request)
        );
    }

    /**
     * GET /api/auth/me
     *
     * Devuelve los datos del usuario autenticado.
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(
            Authentication authentication) {

        return ResponseEntity.ok(
            authService.perfil(authentication.getName())
        );
    }

    /**
     * POST /api/auth/forgot-password
     *
     * Solicita el envío del enlace de recuperación.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgot(
            @Valid @RequestBody ForgotPasswordRequest request) {

        authService.solicitarRecuperacion(request);

        return ResponseEntity.ok(
            Map.of(
                "mensaje",
                "Si el correo está registrado, recibirás instrucciones para recuperar tu cuenta."
            )
        );
    }

    /**
     * POST /api/auth/reset-password
     *
     * Permite establecer una contraseña nueva.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> reset(
            @Valid @RequestBody ResetPasswordRequest request) {

        authService.restablecerPassword(request);

        return ResponseEntity.ok(
            Map.of(
                "mensaje",
                "La contraseña se actualizó correctamente."
            )
        );
    }
}