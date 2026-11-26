package com.betancourt.backend.repository;

import com.betancourt.backend.domain.EstadoCliente;
import com.betancourt.backend.model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {

    Optional<Clientes> findByCedula(String cedula);

    boolean existsByCedula(String cedula);

    List<Clientes> findByEstado(EstadoCliente estado);
}