package com.betancourt.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedores") // O el nombre que le hayan dado en la tabla
public class Proveedor {

    @Id
    private Long id;
    
    // Atributos, getters y setters...
}