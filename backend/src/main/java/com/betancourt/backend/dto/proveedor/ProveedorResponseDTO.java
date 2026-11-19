package com.betancourt.backend.dto;

public record ProveedorResponseDTO(
    Integer idProveedor,
    String nombreEmpresa,
    String telefonoContacto,
    String correoContacto,
    Integer calificacion
) {}
