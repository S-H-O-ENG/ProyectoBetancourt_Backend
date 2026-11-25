package com.betancourt.backend.service;

import com.betancourt.backend.dto.Inventario.ProductosRequestDTO;
import com.betancourt.backend.dto.Inventario.ProductosResponseDTO;
import com.betancourt.backend.model.Productos;
import com.betancourt.backend.repository.ProductosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosService {

    private final ProductosRepository productosRepository;

    public ProductosService(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    public List<ProductosResponseDTO> listarTodos() {
        return productosRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

   // En ProductosService.java
    public ProductosResponseDTO obtenerPorId(Integer id) {
    Productos producto = productosRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + id));
    return toResponse(producto);
}

    public ProductosResponseDTO crear(ProductosRequestDTO dto) {
        // TODO: Validar duplicado según los campos reales de Productos (ej. nombre/código)
        Productos producto = new Productos();
        aplicarDatos(producto, dto);
        return toResponse(productosRepository.save(producto));
    }

    public ProductosResponseDTO actualizar(Integer id, ProductosRequestDTO dto) {
        Productos producto = productosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + id));
        aplicarDatos(producto, dto);
        return toResponse(productosRepository.save(producto));
    }

    public void eliminar(Integer id) {
        if (!productosRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto no encontrado: " + id);
        }
        productosRepository.deleteById(id);
    }

    private void aplicarDatos(Productos producto, ProductosRequestDTO dto) {
        // TODO: Reemplazar por los getters del DTO y setters de la entidad Productos
        // Ejemplo:
        // producto.setNombre(dto.nombre());
        // producto.setCantidad(dto.cantidad());
        // producto.setStockMinimo(dto.stockMinimo());
        // producto.setEstado(calcularEstado(dto.cantidad(), dto.stockMinimo()));
    }

    private ProductosResponseDTO toResponse(Productos producto) {
        // TODO: Ajustar argumentos según los campos definidos en ProductosResponseDTO
        return new ProductosResponseDTO();
    }
}