package com.betancourt.backend.controller;

import com.betancourt.backend.repository.VehiculosRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculosController {

    private final VehiculosRepository vehiculosRepository;

    public VehiculosController(VehiculosRepository vehiculosRepository) {
        this.vehiculosRepository = vehiculosRepository;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        if (!vehiculosRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vehiculosRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}