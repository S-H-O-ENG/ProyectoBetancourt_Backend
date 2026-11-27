package com.betancourt.backend.dto.Clientes;

import com.betancourt.backend.domain.EstadoCliente;

public class ClienteRequestDTO {

    private Long id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private EstadoCliente estado;

    // Datos del vehículo que vienen en el mismo formulario de GestionClientes.jsx
    private String tipoVehiculo;
    private String placa;
    private String marca;
    private String modelo;
    private String color;

    public ClienteRequestDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public EstadoCliente getEstado() { return estado; }
    public void setEstado(EstadoCliente estado) { this.estado = estado; }

    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}