package com.betancourt.backend.repository;

import com.betancourt.backend.domain.enums.UserRole;
import com.betancourt.backend.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<Usuario> findByRoleAndIsActive(UserRole role, Boolean isActive, Pageable pageable);
    Page<Usuario> findByRole(UserRole role, Pageable pageable);
    Page<Usuario> findByIsActive(Boolean isActive, Pageable pageable);

    @Query("select u from Usuario u where lower(u.email) = lower(:correo)")
    Optional<Usuario> findByCorreoIgnoreCase(@Param("correo") String correo);

    @Query("select case when count(u) > 0 then true else false end from Usuario u where lower(u.email) = lower(:correo)")
    boolean existsByCorreoIgnoreCase(@Param("correo") String correo);
}
