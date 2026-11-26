package com.betancourt.backend.repository;

import com.betancourt.backend.model.Vehiculos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculosRepository extends JpaRepository<Vehiculos, Long> {

    Optional<Vehiculos> findByPlaca(String placa);

    boolean existsByPlaca(String placa);
}