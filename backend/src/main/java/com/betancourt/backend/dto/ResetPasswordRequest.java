package com.betancourt.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Datos necesarios para establecer una nueva contraseña.
 */
public record ResetPasswordRequest(

    @NotBlank(message = "El token es obligatorio")
    String token,

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(
        min = 8,
        max = 72,
        message = "La contraseña debe tener entre 8 y 72 caracteres"
    )
    String nuevaPassword

) {}