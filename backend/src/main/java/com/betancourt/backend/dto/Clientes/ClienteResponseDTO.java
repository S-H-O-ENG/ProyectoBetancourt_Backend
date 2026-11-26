package com.betancourt.backend.dto.Clientes;

import com.betancourt.backend.domain.EstadoCliente;
import java.util.List;

public class ClienteResponseDTO {

    private Long id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private EstadoCliente estado;
    private List<VehiculoDTO> vehiculos;

    public ClienteResponseDTO() {}

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

    public List<VehiculoDTO> getVehiculos() { return vehiculos; }
    public void setVehiculos(List<VehiculoDTO> vehiculos) { this.vehiculos = vehiculos; }
}