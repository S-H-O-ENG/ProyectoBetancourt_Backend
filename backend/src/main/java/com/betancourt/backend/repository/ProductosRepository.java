package com.betancourt.backend.repository; // Paquete de repositorios de acceso a datos

import com.betancourt.backend.model.Productos; // Entidad JPA del modelo de Productos
import org.springframework.data.jpa.repository.JpaRepository; // Interfaz base con CRUD y consultas JPA

public interface ProductosRepository extends JpaRepository<Productos, Integer> { // Gestiona la entidad Productos con clave primaria Integer
}