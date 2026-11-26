package com.betancourt.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Solicitud para recuperar una contraseña.
 */
public record ForgotPasswordRequest(

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Ingresa un correo válido")
    String correo

) {}