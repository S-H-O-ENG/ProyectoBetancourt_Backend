package com.betancourt.backend.dto.pedidos; // Mismo paquete que PedidosRequestDTO

public record PedidosResponseDTO( // DTO de respuesta con los campos reales de la entidad Pedidos
        Integer idpedido, // Coincide con @Id private Integer idpedido en Pedidos
        String importancia, // Coincide con private String importancia en Pedidos
        String metodopago // Coincide con private String metodopago en Pedidos
) { // Cierre del record
} // Fin de la clase