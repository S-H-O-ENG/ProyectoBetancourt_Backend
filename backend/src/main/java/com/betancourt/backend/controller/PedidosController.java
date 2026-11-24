package com.betancourt.backend.controller; // Paquete de controladores REST[cite: 4]

import com.betancourt.backend.dto.pedidos.PedidosRequestDTO; // DTO de entrada del pedido[cite: 7]
import com.betancourt.backend.dto.pedidos.PedidosResponseDTO; // DTO de salida del pedido
import com.betancourt.backend.service.PedidosService; // Capa de servicio que ejecuta la lógica
import java.net.URI; // Representa la URI del recurso recién creado
import org.springframework.http.ResponseEntity; // Permite controlar código de estado HTTP y cuerpo de respuesta
import org.springframework.web.bind.annotation.PostMapping; // Mapea peticiones HTTP POST
import org.springframework.web.bind.annotation.RequestBody; // Lee el JSON del cuerpo de la petición
import org.springframework.web.bind.annotation.RequestMapping; // Define la ruta base del controlador
import org.springframework.web.bind.annotation.RestController; // Declara un controlador REST que serializa a JSON
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // Construye dinámicamente la URL del recurso creado

@RestController // Expone endpoints REST en formato JSON
@RequestMapping("/api/pedidos") // Ruta base en minúsculas (coincide mejor con REST y con tu SecurityConfig)
public class PedidosController { // Controlador encargado de los endpoints de pedidos

    private final PedidosService pedidoService; // Referencia inmutable al servicio de pedidos

    public PedidosController(PedidosService pedidoService) { // Inyección del servicio vía constructor
        this.pedidoService = pedidoService; // Guarda la referencia del servicio
    } // Fin del constructor

    @PostMapping // Atiende peticiones POST a /api/pedidos
    public ResponseEntity<PedidosResponseDTO> crearPedido(@RequestBody PedidosRequestDTO request) { // Convierte el JSON entrante a PedidosRequestDTO[cite: 7]
        PedidosResponseDTO creado = pedidoService.crearPedido(request); // Llama al servicio para crear el pedido en BD
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // Toma la URL actual (/api/pedidos)
                .path("/{id}") // Agrega el segmento /{id}
                .buildAndExpand(creado.idpedido()) // Reemplaza {id} con el ID del pedido creado
                .toUri(); // Convierte el resultado en objeto URI
        return ResponseEntity.created(location).body(creado); // Responde HTTP 201 Created, cabecera Location y JSON del pedido
    } // Fin del método crearPedido
} // Fin de la clase