package com.betancourt.backend.model;

import com.betancourt.backend.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String firstName;

    @Column(name = "apellido", nullable = false, length = 100)
    private String lastName;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "telefono", length = 30)
    private String phone;

    @Column(name = "pass", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 40)
    @Builder.Default
    private UserRole role = UserRole.CLIENTE;

    @Column(name = "estado", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ClientProfile clientProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeeProfile employeeProfile;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Compatibilidad con el módulo de autenticación y recuperación existente.
    public Integer getIdusuario() { return id == null ? null : id.intValue(); }
    public void setIdusuario(Integer idusuario) { this.id = idusuario == null ? null : idusuario.longValue(); }
    public String getNombre() { return firstName; }
    public void setNombre(String nombre) { this.firstName = nombre; }
    public String getApellido() { return lastName; }
    public void setApellido(String apellido) { this.lastName = apellido; }
    public String getTelefono() { return phone; }
    public void setTelefono(String telefono) { this.phone = telefono; }
    public String getCorreo() { return email; }
    public void setCorreo(String correo) { this.email = correo; }
    public String getPass() { return passwordHash; }
    public void setPass(String pass) { this.passwordHash = pass; }
    public Boolean getEstado() { return isActive; }
    public void setEstado(Boolean estado) { this.isActive = estado; }
    public String getRol() { return role == null ? null : role.name(); }
    public void setRol(String rol) { this.role = UserRole.valueOf(rol); }
}
