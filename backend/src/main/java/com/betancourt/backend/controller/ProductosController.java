package com.betancourt.backend.controller;

import com.betancourt.backend.dto.Inventario.ProductosRequestDTO;
import com.betancourt.backend.dto.Inventario.ProductosResponseDTO;
import com.betancourt.backend.service.ProductosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductosController {

    private final ProductosService productosService;

    public ProductosController(ProductosService productosService) {
        this.productosService = productosService;
    }

    @GetMapping
    public ResponseEntity<List<ProductosResponseDTO>> listar() {
        return ResponseEntity.ok(productosService.listarTodos());
    }

    // En ProductosController.java
    @GetMapping("/{id}")
    public ResponseEntity<ProductosResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(productosService.obtenerPorId(id));
}

    @PostMapping
    public ResponseEntity<ProductosResponseDTO> crear(@Valid @RequestBody ProductosRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productosService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProductosRequestDTO dto
    ) {
        return ResponseEntity.ok(productosService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}