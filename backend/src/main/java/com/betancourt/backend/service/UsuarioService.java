package com.betancourt.backend.service;

import com.betancourt.backend.dto.user.*;
import com.betancourt.backend.exception.BusinessRuleException;
import com.betancourt.backend.model.Usuario;
import com.betancourt.backend.repository.UsuarioRepository;
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
        if (usuarioRepository.existsByCorreo(emailClean)) {
            throw new BusinessRuleException("Ya existe un colaborador registrado con ese correo.");
        }

        // 3. Crear usuario con contraseña encriptada (BCrypt)
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getFirstName().trim());
        usuario.setApellido(dto.getLastName().trim());
        usuario.setCorreo(emailClean);
        usuario.setTelefono(dto.getPhone());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(dto.getRole());
        usuario.setActivo(true);

        Usuario saved = usuarioRepository.save(usuario);
        return mapToResponse(saved);
    }

    private UserResponseDTO mapToResponse(Usuario u) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(u.getIdUsuario());
        dto.setFirstName(u.getNombre());
        dto.setLastName(u.getApellido());
        dto.setEmail(u.getCorreo());
        dto.setPhone(u.getTelefono());
        dto.setRole(u.getRol());
        dto.setIsActive(u.getActivo());
        return dto;
    }
}
