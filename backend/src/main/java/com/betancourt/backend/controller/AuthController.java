package com.betancourt.backend.controller;

import com.betancourt.backend.model.Usuario;
import com.betancourt.backend.repository.UsuarioRepository;
import com.betancourt.backend.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UsuarioRepository usuarioRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    public record LoginRequest(
            @NotBlank @Email String correo,
            @NotBlank String password
    ) {}

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.correo(), request.password())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", token);
        response.put("tokenType", "Bearer");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getProfile(Authentication authentication) {
        Usuario usuario = usuarioRepository.findByCorreo(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Map<String, Object> profile = new HashMap<>();
        profile.put("idUsuario", usuario.getIdUsuario());
        profile.put("nombre", usuario.getNombre());
        profile.put("apellido", usuario.getApellido());
        profile.put("correo", usuario.getCorreo());
        profile.put("rol", usuario.getRol());
        return ResponseEntity.ok(profile);
    }
}
