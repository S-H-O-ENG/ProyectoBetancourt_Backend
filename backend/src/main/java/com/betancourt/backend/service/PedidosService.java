package com.betancourt.backend.service;

import com.betancourt.backend.dto.pedidos.PedidosRequestDTO;
import com.betancourt.backend.dto.pedidos.PedidosResponseDTO;
import java.util.List;

public interface PedidosService {

    PedidosResponseDTO crearPedido(PedidosRequestDTO request);

    List<PedidosResponseDTO> listarPedidos();

    PedidosResponseDTO actualizarEstado(Integer id, String nuevoEstado);
}