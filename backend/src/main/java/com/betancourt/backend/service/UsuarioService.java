package com.betancourt.backend.service;

import com.betancourt.backend.domain.enums.UserRole;
import com.betancourt.backend.dto.user.*;
import com.betancourt.backend.exception.BusinessRuleException;
import com.betancourt.backend.exception.ResourceNotFoundException;
import com.betancourt.backend.model.*;
import com.betancourt.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto) {
        // Validaciones de perfiles según rol
        if (dto.getRole() == UserRole.SUPER_ADMIN) {
            throw new BusinessRuleException("El SUPER_ADMIN no se puede crear desde este endpoint.");
        }

        if (dto.getRole() == UserRole.CLIENTE) {
            if (dto.getClientProfile() == null) throw new BusinessRuleException("El cliente debe tener un perfil de cliente.");
            if (dto.getEmployeeProfile() != null) throw new BusinessRuleException("Un cliente no puede tener perfil de empleado.");
        } else if (dto.getRole() == UserRole.ADMIN || dto.getRole() == UserRole.MECANICO) {
            if (dto.getEmployeeProfile() == null) throw new BusinessRuleException("El empleado debe tener un perfil de empleado.");
            if (dto.getClientProfile() != null) throw new BusinessRuleException("Un empleado no puede tener perfil de cliente.");
        }

        String emailClean = dto.getEmail().trim().toLowerCase();
        if (usuarioRepository.existsByEmail(emailClean)) {
            throw new BusinessRuleException("Ya existe un usuario registrado con ese correo.");
        }

        Usuario usuario = Usuario.builder()
                .firstName(dto.getFirstName().trim())
                .lastName(dto.getLastName().trim())
                .email(emailClean)
                .phone(dto.getPhone())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .isActive(true)
                .build();

        if (dto.getRole() == UserRole.CLIENTE) {
            ClientProfile cp = ClientProfile.builder()
                    .user(usuario)
                    .birthDate(dto.getClientProfile().getBirthDate())
                    .address(dto.getClientProfile().getAddress())
                    .emergencyContactName(dto.getClientProfile().getEmergencyContactName())
                    .emergencyContactPhone(dto.getClientProfile().getEmergencyContactPhone())
                    .notes(dto.getClientProfile().getNotes())
                    .build();
            usuario.setClientProfile(cp);
        } else {
            EmployeeProfile ep = EmployeeProfile.builder()
                    .user(usuario)
                    .jobTitle(dto.getEmployeeProfile().getJobTitle())
                    .specialty(dto.getEmployeeProfile().getSpecialty())
                    .hireDate(dto.getEmployeeProfile().getHireDate())
                    .isAvailable(dto.getEmployeeProfile().getIsAvailable())
                    .notes(dto.getEmployeeProfile().getNotes())
                    .build();
            usuario.setEmployeeProfile(ep);
        }

        Usuario saved = usuarioRepository.save(usuario);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario solicitado."));
        return mapToResponse(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> listUsers(UserRole role, Boolean isActive, Pageable pageable) {
        Page<Usuario> users;
        if (role != null && isActive != null) {
            users = usuarioRepository.findByRoleAndIsActive(role, isActive, pageable);
        } else if (role != null) {
            users = usuarioRepository.findByRole(role, pageable);
        } else if (isActive != null) {
            users = usuarioRepository.findByIsActive(isActive, pageable);
        } else {
            users = usuarioRepository.findAll(pageable);
        }
        return users.map(this::mapToResponse);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario solicitado."));

        if (user.getRole() == UserRole.SUPER_ADMIN) {
            throw new BusinessRuleException("No se puede modificar el SUPER_ADMIN desde este módulo.");
        }

        if (dto.getEmail() != null) {
            String cleanEmail = dto.getEmail().trim().toLowerCase();
            usuarioRepository.findByEmail(cleanEmail).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new BusinessRuleException("Ese correo electrónico ya está registrado.");
                }
            });
            user.setEmail(cleanEmail);
        }

        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName().trim());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName().trim());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone().trim());

        return mapToResponse(usuarioRepository.save(user));
    }

    @Transactional
    public UserResponseDTO changeStatus(Long id, Boolean isActive, Long currentUserId) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario solicitado."));

        if (user.getRole() == UserRole.SUPER_ADMIN) {
            throw new BusinessRuleException("No se puede cambiar el estado del SUPER_ADMIN.");
        }

        if (user.getId().equals(currentUserId) && !isActive) {
            throw new BusinessRuleException("No puedes desactivar tu propia cuenta.");
        }

        user.setIsActive(isActive);
        return mapToResponse(usuarioRepository.save(user));
    }

    @Transactional
    public UserResponseDTO changeStatus(Long id, Boolean isActive, String currentUserEmail) {
        Usuario currentUser = usuarioRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario autenticado."));
        return changeStatus(id, isActive, currentUser.getId());
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