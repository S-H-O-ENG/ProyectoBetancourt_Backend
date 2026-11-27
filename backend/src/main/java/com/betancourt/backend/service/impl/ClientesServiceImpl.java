package com.betancourt.backend.service.impl;

import com.betancourt.backend.domain.EstadoCliente;
import com.betancourt.backend.dto.Clientes.ClienteRequestDTO;
import com.betancourt.backend.dto.Clientes.ClienteResponseDTO;
import com.betancourt.backend.dto.Clientes.VehiculoDTO;
import com.betancourt.backend.model.Clientes;
import com.betancourt.backend.model.Vehiculos;
import com.betancourt.backend.repository.ClientesRepository;
import com.betancourt.backend.repository.VehiculosRepository;
import com.betancourt.backend.service.ClientesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientesServiceImpl implements ClientesService {

    private final ClientesRepository clientesRepository;
    private final VehiculosRepository vehiculosRepository;

    public ClientesServiceImpl(ClientesRepository clientesRepository,
                               VehiculosRepository vehiculosRepository) {
        this.clientesRepository = clientesRepository;
        this.vehiculosRepository = vehiculosRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientes() {
        return clientesRepository.findAll()
                .stream()
                .map(this::mapearAResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerPorId(Long id) {
        Clientes cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        return mapearAResponseDTO(cliente);
    }

    @Override
@Transactional
public ClienteResponseDTO guardarCliente(ClienteRequestDTO dto) {
    Clientes cliente = clientesRepository.findByCedula(dto.getCedula())
            .orElseGet(() -> {
                Clientes nuevo = new Clientes();
                nuevo.setCedula(dto.getCedula());
                nuevo.setNombre(dto.getNombre());
                nuevo.setApellido(dto.getApellido());
                nuevo.setTelefono(dto.getTelefono());
                nuevo.setCorreo(dto.getCorreo());
                nuevo.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoCliente.ACTIVO);
                return clientesRepository.save(nuevo);
            });

    if (dto.getPlaca() != null && !dto.getPlaca().isBlank()) {
        Vehiculos vehiculo = new Vehiculos();
        vehiculo.setTipoVehiculo(dto.getTipoVehiculo());
        vehiculo.setPlaca(dto.getPlaca().trim().toUpperCase());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setColor(dto.getColor());
        vehiculo.setCliente(cliente);
        vehiculosRepository.save(vehiculo);
    }

    return obtenerPorId(cliente.getId());
}

    @Override
    @Transactional
    public ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO dto) {
        Clientes cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));

        cliente.setCedula(dto.getCedula());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setTelefono(dto.getTelefono());
        cliente.setCorreo(dto.getCorreo());
        if (dto.getEstado() != null) {
            cliente.setEstado(dto.getEstado());
        }

        Clientes actualizado = clientesRepository.save(cliente);
        return mapearAResponseDTO(actualizado);
    }

    @Override
    @Transactional
    public ClienteResponseDTO cambiarEstado(Long id, EstadoCliente estado) {
        Clientes cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        cliente.setEstado(estado);
        return mapearAResponseDTO(clientesRepository.save(cliente));
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        if (!clientesRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
        clientesRepository.deleteById(id);
    }

    private ClienteResponseDTO mapearAResponseDTO(Clientes cliente) {
        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setId(cliente.getId());
        response.setCedula(cliente.getCedula());
        response.setNombre(cliente.getNombre());
        response.setApellido(cliente.getApellido());
        response.setTelefono(cliente.getTelefono());
        response.setCorreo(cliente.getCorreo());
        response.setEstado(cliente.getEstado());

        List<VehiculoDTO> vehiculosDTO = new ArrayList<>();
        if (cliente.getVehiculos() != null) {
            for (Vehiculos v : cliente.getVehiculos()) {
                VehiculoDTO vDto = new VehiculoDTO();
                vDto.setId(v.getId());
                vDto.setTipoVehiculo(v.getTipoVehiculo());
                vDto.setPlaca(v.getPlaca());
                vDto.setMarca(v.getMarca());
                vDto.setModelo(v.getModelo());
                vDto.setColor(v.getColor());
                vehiculosDTO.add(vDto);
            }
        }
        response.setVehiculos(vehiculosDTO);
        return response;
    }
}