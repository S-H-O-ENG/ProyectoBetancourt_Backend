package com.betancourt.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betancourt.backend.domain.enums.UserRole;
import com.betancourt.backend.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<Usuario> findByRoleAndIsActive(UserRole role, Boolean isActive, Pageable pageable);
    Page<Usuario> findByRole(UserRole role, Pageable pageable);
    Page<Usuario> findByIsActive(Boolean isActive, Pageable pageable);
}