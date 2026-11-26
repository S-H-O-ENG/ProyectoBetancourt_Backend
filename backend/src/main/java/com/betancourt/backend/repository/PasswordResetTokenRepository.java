package com.betancourt.backend.repository;

import com.betancourt.backend.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para administrar los tokens
 * de recuperación de contraseña.
 */
public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, Long> {

    // Busca el registro utilizando el hash del token.
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    // Elimina los tokens anteriores de un usuario.
    void deleteByUsuario_Idusuario(Integer idusuario);
}