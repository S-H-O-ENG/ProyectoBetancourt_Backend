package com.betancourt.backend.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

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

    // Relación OneToMany: Un producto tiene muchos movimientos
    // mappedBy apunta al atributo 'producto' en la clase Movimientos
    @OneToMany(mappedBy = "producto")
    private List<Movimientos> movimientos_Inventario = new ArrayList<>();

    public Productos() {
    }

    public Productos(Integer id_Producto, String nombre, Integer cantidad, Double precio, String estado) {
        this.id_Producto = id_Producto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.estado = estado;
    }

    // Getters y Setters

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
        return movimientos_Inventario;
    }

    public void setMovimientos_Inventario(List<Movimientos> movimientos_Inventario) {
        this.movimientos_Inventario = movimientos_Inventario;
    }
    
    
    public void addMovimiento(Movimientos movimiento) {
        movimientos_Inventario.add(movimiento);
        movimiento.setProducto(this);
    }

    public void removeMovimiento(Movimientos movimiento) {
        movimientos_Inventario.remove(movimiento);
        movimiento.setProducto(null);
    }
}