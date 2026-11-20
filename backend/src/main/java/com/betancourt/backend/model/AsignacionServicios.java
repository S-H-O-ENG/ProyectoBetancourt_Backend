
package com.betancourt.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "asignacionservicios")
public class AsignacionServicios {

    @Id
    private Long idAsignacion;

    private LocalDate fecha;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private String observaciones;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtrabajador")
    private Trabajadores trabajador;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idservicio")
    private Servicios servicio;

    public AsignacionServicios() {}

    

    public Long getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(Long idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Trabajadores getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajadores trabajador) {
        this.trabajador = trabajador;
    }

    public Servicios getServicio() {
        return servicio;
    }

    public void setServicio(Servicios servicio) {
        this.servicio = servicio;
    }
}
