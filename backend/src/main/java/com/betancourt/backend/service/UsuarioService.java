package com.betancourt.backend.service;

import com.betancourt.backend.dto.user.*;
import com.betancourt.backend.exception.BusinessRuleException;
import com.betancourt.backend.exception.ResourceNotFoundException;
import com.betancourt.backend.model.Usuario;
import com.betancourt.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto) {
        String emailClean = dto.getEmail().trim().toLowerCase();

        // 1. Validar dominio obligatorio @betancourt
        if (!emailClean.endsWith("@betancourt.com") && !emailClean.endsWith("@betancourt.co") && !emailClean.contains("@betancourt")) {
            throw new BusinessRuleException("El correo debe pertenecer al dominio institucional @betancourt");
        }

        // 2. Validar correo no duplicado
        if (usuarioRepository.existsByEmail(emailClean)) {
            throw new BusinessRuleException("Ya existe un colaborador registrado con ese correo.");
        }

        // 3. Crear usuario con contraseña encriptada (BCrypt)
        Usuario usuario = Usuario.builder()
                .firstName(dto.getFirstName().trim())
                .lastName(dto.getLastName().trim())
                .email(emailClean)
                .phone(dto.getPhone())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .isActive(true)
                .build();

        Usuario saved = usuarioRepository.save(usuario);
        return mapToResponse(saved);
    }

    private UserResponseDTO mapToResponse(Usuario u) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(u.getId());
        dto.setFirstName(u.getFirstName());
        dto.setLastName(u.getLastName());
        dto.setEmail(u.getEmail());
        dto.setPhone(u.getPhone());
        dto.setRole(u.getRole());
        dto.setIsActive(u.getIsActive());
        dto.setCreatedAt(u.getCreatedAt());
        dto.setUpdatedAt(u.getUpdatedAt());
        return dto;
    }
}
