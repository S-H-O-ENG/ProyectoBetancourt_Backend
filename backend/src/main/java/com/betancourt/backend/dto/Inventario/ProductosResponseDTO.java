package com.betancourt.backend.dto.Inventario;

public class ProductosResponseDTO {

    private Integer id;
    private String nombre;
    private Integer cantidad;
    private Integer stockMinimo;
    private String estado;
    private String tipoVehiculo;

    public ProductosResponseDTO() {
    }

    public ProductosResponseDTO(
            Integer id,
            String nombre,
            Integer cantidad,
            Integer stockMinimo,
            String estado,
            String tipoVehiculo
    ) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.stockMinimo = stockMinimo;
        this.estado = estado;
        this.tipoVehiculo = tipoVehiculo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }



    
}
