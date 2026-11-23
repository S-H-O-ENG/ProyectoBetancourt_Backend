package com.betancourt.backend.dto.proveedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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