package com.betancourt.backend.model;

<<<<<<< HEAD
import jakarata.persistence.Entity;
import jakarata.persistence.Generatedvalue;
import jakarata.persistence.GenerationType;
import jakarata.persistence.id;
import jakarata.persistence.table;


@Entity

@table(name = "Productos")

public class Productos{
    @id
    @Generatedvalue(strategy = GenerationType.IDENTITY)
    Private Integer ID_Producto;
    Private String Nombre;   
    Private Integer Cantidad;
    Private Double Precio;
    Private String Estado;

    public Productos(){
    }

    public Productos(Integer ID_Producto,Varchar Nombre,Integer Cantidad,Double Precio, Varchar Estado){
     this.ID_Producto = ID_Producto;  
     this.Nombre = Nombre;   
     this.Cantidad = Cantidad;
     this.Precio = Precio;
     this.Estado = Estado;
    }


    public Integer getID_Producto(){
        return ID_Producto;
    }

    public void setID_Producto(Integer ID_Producto){
        this.ID_Producto = ID_Producto;
    }

    public String getNombre(){
        return Nombre;
    }

    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }

    public Integer getCantidad(){
        return Cantidad;
    }

    public void setCantidad(Integer Cantidad){
        this.Cantidad = Cantidad;
    }

    public Double getPrecio(){
        return Precio;
    }

    public void setPrecio(Double Precio){
        this.Precio = Precio;
    }

    public String getEstado(){
        return Estado;
    }

    public void setEstado(String Estado){
        this.Estado = Estado;
    }

}
=======
public class Productos {
    
}
>>>>>>> e0802dc43fe5ef48f6355db3c8369b2a555c5ba7
