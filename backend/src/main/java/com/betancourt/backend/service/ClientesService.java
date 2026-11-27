package com.betancourt.backend.service;

import com.betancourt.backend.domain.EstadoCliente;
import com.betancourt.backend.dto.Clientes.ClienteRequestDTO;
import com.betancourt.backend.dto.Clientes.ClienteResponseDTO;

import java.util.List;

public interface ClientesService {

    List<ClienteResponseDTO> listarClientes();

    ClienteResponseDTO obtenerPorId(Long id);

    ClienteResponseDTO guardarCliente(ClienteRequestDTO requestDTO);

    ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO requestDTO);

    ClienteResponseDTO cambiarEstado(Long id, EstadoCliente estado);

    void eliminarCliente(Long id);
}