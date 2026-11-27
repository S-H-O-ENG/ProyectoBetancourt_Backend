package com.betancourt.backend.controller;

import com.betancourt.backend.dto.ForgotPasswordRequest;
import com.betancourt.backend.dto.LoginRequest;
import com.betancourt.backend.dto.ResetPasswordRequest;
import com.betancourt.backend.service.AuthService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/perfil")
    public ResponseEntity<Map<String, Object>> perfil(Principal principal) {
        return ResponseEntity.ok(authService.perfil(principal.getName()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> solicitarRecuperacion(
            @Valid @RequestBody ForgotPasswordRequest request) {
        authService.solicitarRecuperacion(request);
        return ResponseEntity.ok(Map.of(
            "mensaje", "Si el correo está registrado, recibirás instrucciones para recuperar tu contraseña."
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> restablecerPassword(
            @Valid @RequestBody ResetPasswordRequest request) {
        authService.restablecerPassword(request);
        return ResponseEntity.noContent().build();
    }
}
