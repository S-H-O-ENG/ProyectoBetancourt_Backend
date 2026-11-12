package com.betancourt.backend.model;

import java.util.ArrayList;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Productos")

public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_Producto;

    private String nombre;
    private Integer cantidad;
    private Double precio;
    private String estado;

    @OneToMany(mappedBy = "Productos")
    private List<Movimientos> Movimientos_Inventario = new ArrayList<>();

    public Productos() {
    }

    public Productos(Integer id_Producto, String nombre, Integer cantidad, Double precio, String estado) {
        this.id_Producto = id_Producto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.estado = estado;
    }

    public Integer getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(Integer id_Producto) {
        this.id_Producto = id_Producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Movimientos> getMovimientos_Inventario() {
        return Movimientos_Inventario;
    }

    public void setMovimientos_Inventario(List<Movimientos> movimientos_Inventario) {
        Movimientos_Inventario = movimientos_Inventario;
    }



}
