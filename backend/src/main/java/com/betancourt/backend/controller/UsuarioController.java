package com.betancourt.backend.controller;

import com.betancourt.backend.domain.enums.UserRole;
import com.betancourt.backend.dto.user.*;
import com.betancourt.backend.model.Usuario;
import com.betancourt.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUser(dto));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> listUsers(
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(usuarioService.listUsers(role, isActive, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.updateUser(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponseDTO> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody UserStatusUpdateDTO dto,
            @AuthenticationPrincipal Usuario currentUser) {
        return ResponseEntity.ok(usuarioService.changeStatus(id, dto.getIsActive(), currentUser.getId()));
    }
}
