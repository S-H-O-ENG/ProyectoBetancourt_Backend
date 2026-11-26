package com.betancourt.backend.dto.pedidos;

public record PedidosRequestDTO(
        String nombre,
        String apellido,
        String rol,
        String proveedor,
        String repuesto,
        Integer cantidad,
        String importancia,
        String metodopago
) {
}