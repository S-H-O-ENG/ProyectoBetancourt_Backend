package com.betancourt.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;



@Entity
@Table(name = "Movimientos_Inventario")

public class Movimientos{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_movimiento;

    private LocalDateTime fechahora = LocalDateTime.now();
    private Integer cantidad;
    private String observacion;

    //CORRIJAL LAS RELACIONES DAVOT
    //@ManyToOne(fetch = fetchType.LAZY)
    //@JoinColumn(name = "ID_Producto", nullable = false)
    //private Productos productos;

    public Movimientos(){
    }

    public Movimientos(Integer id_movimiento, LocalDateTime fechahora, Integer cantidad, String observacion,
            Productos productos) {
        this.id_movimiento = id_movimiento;
        this.fechahora = fechahora;
        this.cantidad = cantidad;
        this.observacion = observacion;
    }

    public Integer getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(Integer id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}