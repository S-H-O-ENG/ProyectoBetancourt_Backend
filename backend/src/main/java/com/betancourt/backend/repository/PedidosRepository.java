package com.betancourt.backend.repository; // Paquete de repositorios de acceso a datos

import com.betancourt.backend.model.Pedidos; // Entidad JPA del modelo de pedidos
import org.springframework.data.jpa.repository.JpaRepository; // Interfaz base con CRUD y consultas JPA

public interface PedidosRepository extends JpaRepository<Pedidos, Integer> { // Gestiona la entidad Pedidos con clave primaria Integer
} // Fin de la interfaz