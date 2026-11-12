package com.betancourt.backend.model;

import jakarata.persistence.Entity;
import jakarata.persistence.Generatedvalue;
import jakarata.persistence.GenerationType;
import jakarata.persistence.id;
import jakarata.persistence.table;


@Entity

@table(name = "Movimientos_Inventario")

public class Movimientos{
    @id
    @Generatedvalue(strategy = GenerationType.IDENTITY)
    Private Integer ID_movimiento;
    Private DateTime Fecha;   
    Private Integer Cantidad;
    Private String Observacion;

    @ManyToOne(fetch = fetchType.LAZY)
    @JoinColumn(name = "ID_Producto", nullable = false)
    private Productos productos;

    public Movimientos(){
    }

    public Movimientos(Integer ID_movimiento,DateTime Fecha,Integer Cantidad, Varchar Observacion){
     this.ID_movimiento = ID_movimiento;  
     this.Fecha = Fecha;   
     this.Cantidad = Cantidad;
     this.Observacion = Observacion;
    }


    public Integer getID_movimiento(){
        return ID_movimiento;
    }

    public void setID_movimiento(Integer ID_movimiento){
        this.ID_movimiento = ID_movimiento;
    }

    public String getFecha(){
        return Fecha;
    }

    public void setFecha(String Fecha){
        this.Fecha = Fecha;
    }

    public Integer getCantidad(){
        return Cantidad;
    }

    public void setCantidad(Integer Cantidad){
        this.Cantidad = Cantidad;
    }

    public String getObservacion(){
        return Observacion;
    }

    public void setObservacion(String Observacion){
        this.Observacion = Observacion;
    }

}