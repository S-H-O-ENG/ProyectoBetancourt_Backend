package com.betancourt.backend.dto;

import jakarta.validation.constraints.*;

public record ProveedorRequestDTO(
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 80)
    String nombreEmpresa,

    @Size(max = 15)
    String telefonoContacto,

    @Email(message = "Formato de correo inválido")
    @Size(max = 120)
    String correoContacto,

    @Min(1) @Max(5)
    Integer calificacion
) {}