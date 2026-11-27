package com.betancourt.backend.controller;

import com.betancourt.backend.domain.EstadoCliente;
import com.betancourt.backend.dto.Clientes.ClienteRequestDTO;
import com.betancourt.backend.dto.Clientes.ClienteResponseDTO;
import com.betancourt.backend.service.ClientesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClientesController {

    private final ClientesService clientesService;

    public ClientesController(ClientesService clientesService) {
        this.clientesService = clientesService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        return ResponseEntity.ok(clientesService.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clientesService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody ClienteRequestDTO requestDTO) {
        ClienteResponseDTO creado = clientesService.guardarCliente(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(@PathVariable Long id,
                                                              @RequestBody ClienteRequestDTO requestDTO) {
        return ResponseEntity.ok(clientesService.actualizarCliente(id, requestDTO));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ClienteResponseDTO> cambiarEstado(@PathVariable Long id,
                                                            @RequestParam EstadoCliente estado) {
        return ResponseEntity.ok(clientesService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clientesService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}