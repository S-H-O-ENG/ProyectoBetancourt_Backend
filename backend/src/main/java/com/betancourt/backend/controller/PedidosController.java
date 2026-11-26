package com.betancourt.backend.controller;

import com.betancourt.backend.dto.pedidos.PedidosRequestDTO;
import com.betancourt.backend.dto.pedidos.PedidosResponseDTO;
import com.betancourt.backend.service.PedidosService;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

    private final PedidosService pedidosService;

    public PedidosController(PedidosService pedidosService) {
        this.pedidosService = pedidosService;
    }

    // 1. RECIBIR UN PEDIDO NUEVO DESDE REACT (POST /api/pedidos)
    @PostMapping
    public ResponseEntity<PedidosResponseDTO> crearPedido(@RequestBody PedidosRequestDTO request) {
        PedidosResponseDTO creado = pedidosService.crearPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // 2. ENVIAR TODOS LOS PEDIDOS A REACT PARA VERLOS (GET /api/pedidos)
    @GetMapping
    public ResponseEntity<List<PedidosResponseDTO>> listarPedidos() {
        List<PedidosResponseDTO> pedidos = pedidosService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    // 3. ACTUALIZAR ESTADO DEL PEDIDO (PATCH /api/pedidos/{id}/estado)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidosResponseDTO> actualizarEstado(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        PedidosResponseDTO actualizado = pedidosService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok(actualizado);
    }
}