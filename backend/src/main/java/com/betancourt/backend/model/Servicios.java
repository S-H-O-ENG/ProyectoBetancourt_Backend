package com.betancourt.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servicios")
public class Servicios {

    @Id
    private Long idServicio;

    private String nombre;

    private String descripcion;

    private String duracion;

   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcliente")
    private Clientes cliente;

    // Relación con Cotizaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcotizacion")
    private Cotizaciones cotizacion;

    public Servicios() {}

    

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Cotizaciones getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizaciones cotizacion) {
        this.cotizacion = cotizacion;
    }
}