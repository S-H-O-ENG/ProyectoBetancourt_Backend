
package com.betancourt.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trabajador")
public class Trabajadores {

    @Id
    private Long idTrabajador;

    @Column(length = 45)
    private String cargo;

    private Double salario;

    public Trabajadores() {}

    // Getters y Setters

    public Long getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Long idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }
}
