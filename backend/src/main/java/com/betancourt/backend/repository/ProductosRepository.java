package com.betancourt.backend.repository;

import com.betancourt.backend.*;
import com.betancourt.backend.model.Productos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {

    List<Productos> findByNombreContainingIgnoreCase(String nombre);

    List<Productos> findByCantidad(Integer cantidad);
}