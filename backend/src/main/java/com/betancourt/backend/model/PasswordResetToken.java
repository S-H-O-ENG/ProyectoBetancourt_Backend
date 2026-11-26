package com.betancourt.backend.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Entidad para almacenar los tokens de recuperación.
 *
 * El token original no se almacena:
 * únicamente se guarda su hash SHA-256.
 */
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hash único del token de recuperación.
    @Column(nullable = false, unique = true, length = 64)
    private String tokenHash;

    // Usuario propietario de la solicitud.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Fecha y hora de vencimiento.
    @Column(nullable = false)
    private Instant expiresAt;

    // Indica si el token ya fue utilizado.
    @Column(nullable = false)
    private boolean used = false;

    public PasswordResetToken() {
    }

    public Long getId() {
        return id;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}