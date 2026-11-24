
package com.betancourt.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cotizaciones")

public class Cotizaciones {

    @Id
    private Integer idCotizacion;

    private String estado;
    private LocalDate fecha;
    private Double total;

    public Cotizaciones() {
    }

    public Cotizaciones(Integer idCotizacion, String estado, LocalDate fecha, Double total) {
        this.idCotizacion = idCotizacion;
        this.estado = estado;
        this.fecha = fecha;
        this.total = total;
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
