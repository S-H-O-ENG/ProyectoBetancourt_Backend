package com.betancourt.backend.service;

import com.betancourt.backend.dto.Inventario.ProductosRequestDTO;
import com.betancourt.backend.dto.Inventario.ProductosResponseDTO;
import com.betancourt.backend.model.Productos;
import com.betancourt.backend.repository.ProductosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {

    private final ProductosRepository productosRepository;

    public ProductoService(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductosResponseDTO> listarTodos() {
        return productosRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductosResponseDTO obtenerPorId(Integer id) {
        Productos producto = productosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + id));
        return toResponse(producto);
    }

    @Transactional
    public ProductosResponseDTO crear(ProductosRequestDTO dto) {
        Productos producto = new Productos();
        aplicarDatos(producto, dto);
        return toResponse(productosRepository.save(producto));
    }

    @Transactional
    public ProductosResponseDTO actualizar(Integer id, ProductosRequestDTO dto) {
        Productos producto = productosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + id));
        aplicarDatos(producto, dto);
        return toResponse(productosRepository.save(producto));
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!productosRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto no encontrado: " + id);
        }
        productosRepository.deleteById(id);
    }

    private void aplicarDatos(Productos producto, ProductosRequestDTO dto) {
        // Completa aquí con los campos reales de Productos y ProductosRequestDTO
    }

    private ProductosResponseDTO toResponse(Productos producto) {
        // Completa aquí con los campos reales de ProductosResponseDTO y Productos
        return new ProductosResponseDTO();
    }
}