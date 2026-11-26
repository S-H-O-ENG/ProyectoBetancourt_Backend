package com.betancourt.backend.controller;

import com.betancourt.backend.dto.Inventario.ProductosRequestDTO;
import com.betancourt.backend.dto.Inventario.ProductosResponseDTO;
import com.betancourt.backend.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductosController {

    private final ProductoService productoService;

    public ProductosController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductosResponseDTO>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    // En ProductosController.java
    @GetMapping("/{id}")
    public ResponseEntity<ProductosResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
}

    @PostMapping
    public ResponseEntity<ProductosResponseDTO> crear(@Valid @RequestBody ProductosRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProductosRequestDTO dto
    ) {
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}