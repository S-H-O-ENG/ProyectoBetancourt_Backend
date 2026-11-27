package com.betancourt.backend.service.impl;

import com.betancourt.backend.dto.pedidos.PedidosRequestDTO;
import com.betancourt.backend.dto.pedidos.PedidosResponseDTO;
import com.betancourt.backend.exception.ResourceNotFoundException;
import com.betancourt.backend.model.Pedidos;
import com.betancourt.backend.repository.PedidosRepository;
import com.betancourt.backend.service.PedidosService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PedidosServiceImpl implements PedidosService {

    private final PedidosRepository pedidosRepository;

    public PedidosServiceImpl(PedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }

    @Override
    public PedidosResponseDTO crearPedido(PedidosRequestDTO request) {
        Pedidos pedido = new Pedidos();
        pedido.setNombre(request.nombre());
        pedido.setApellido(request.apellido());
        pedido.setRol(request.rol());
        pedido.setProveedor(request.proveedor());
        pedido.setRepuesto(request.repuesto());
        pedido.setCantidad(request.cantidad());
        pedido.setImportancia(request.importancia());
        pedido.setMetodopago(request.metodopago());
        pedido.setEstado("Pendiente");

        Pedidos guardado = pedidosRepository.save(pedido);
        return mapToResponse(guardado);
    }

    @Override
    public List<PedidosResponseDTO> listarPedidos() {
        return pedidosRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PedidosResponseDTO actualizarEstado(Integer id, String nuevoEstado) {
        Pedidos pedido = pedidosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));

        pedido.setEstado(nuevoEstado);
        Pedidos actualizado = pedidosRepository.save(pedido);

        return mapToResponse(actualizado);
    }

    private PedidosResponseDTO mapToResponse(Pedidos pedido) {
        return new PedidosResponseDTO(
                pedido.getIdpedido(),
                pedido.getNombre(),
                pedido.getApellido(),
                pedido.getRol(),
                pedido.getProveedor(),
                pedido.getRepuesto(),
                pedido.getCantidad(),
                pedido.getImportancia(),
                pedido.getMetodopago());
    }
}