package com.betancourt.backend.service;

import com.betancourt.backend.dto.proveedor.ProveedorRequestDTO;
import com.betancourt.backend.dto.proveedor.ProveedorResponseDTO;
import com.betancourt.backend.model.Proveedor;
import com.betancourt.backend.repository.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<ProveedorResponseDTO> listarTodos() {
        return proveedorRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ProveedorResponseDTO obtenerPorId(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado: " + id));
        return toResponse(proveedor);
    }

    public ProveedorResponseDTO crear(ProveedorRequestDTO dto) {
        if (proveedorRepository.existsByNit(dto.nombreEmpresa())) {
            throw new IllegalArgumentException("Ya existe un proveedor con ese nombre");
        }

        Proveedor proveedor = new Proveedor();
        aplicarDatos(proveedor, dto);
        return toResponse(proveedorRepository.save(proveedor));
    }

    public ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado: " + id));
        aplicarDatos(proveedor, dto);
        return toResponse(proveedorRepository.save(proveedor));
    }

    public void eliminar(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Proveedor no encontrado: " + id);
        }
        proveedorRepository.deleteById(id);
    }

    private void aplicarDatos(Proveedor proveedor, ProveedorRequestDTO dto) {
        proveedor.setNombre(dto.nombreEmpresa());
        proveedor.setTelefono(dto.telefonoContacto());
        proveedor.setCorreo(dto.correoContacto());
    }

    private ProveedorResponseDTO toResponse(Proveedor proveedor) {
        Integer idProveedor = proveedor.getId() == null ? null : Math.toIntExact(proveedor.getId());
        return new ProveedorResponseDTO(
                idProveedor,
                proveedor.getNombre(),
                proveedor.getTelefono(),
                proveedor.getCorreo(),
                null
        );
    }
}
