package com.betancourt.backend.service.impl; // Paquete de implementaciones de servicio / DTO con los datos devueltos al cliente
import com.betancourt.backend.dto.pedidos.*;
import com.betancourt.backend.model.Pedidos; // Entidad JPA mapeada a la tabla "pedidos"
import com.betancourt.backend.repository.PedidosRepository; // Repositorio JPA para guardar en PostgreSQL
import com.betancourt.backend.service.PedidosService; // Interfaz que define crearPedido
import org.springframework.stereotype.Service; // Registra esta clase como servicio gestionado por Spring

@Service // Indica a Spring que esta clase contiene lógica de negocio y se puede inyectar
public class PedidoServiceImpl implements PedidosService { // Implementa el contrato de PedidoService

    private final PedidosRepository pedidoRepository; // Instancia inyectada del repositorio (en minúscula, no el nombre de la interfaz)

    public PedidoServiceImpl(PedidosRepository pedidoRepository) { // Constructor que recibe PedidoRepository desde Spring
        this.pedidoRepository = pedidoRepository; // Guarda la instancia del repositorio en el atributo de la clase
    } // Fin del constructor

    @Override // Sobrescribe el método crearPedido declarado en PedidoService
    public PedidosResponseDTO crearPedido(PedidosRequestDTO request) { // Recibe el DTO de entrada y retorna PedidosResponseDTO
        Pedidos pedido = new Pedidos(); // Crea un nuevo objeto Pedidos vacío para llenar sus datos
        pedido.setImportancia(request.importancia()); // Copia la importancia desde el DTO (ajusta si en tu DTO es getter como getImportancia())
        pedido.setMetodopago(request.metodopago()); // Copia el método de pago desde el DTO (ajusta si en tu DTO es getter como getMetodopago())
        // Nota: como Pedidos no tiene campo "estado" aún, no se llama a setEstado(...)

        Pedidos guardado = pedidoRepository.save(pedido); // Usa la instancia `pedidoRepository` (minúscula) para insertar en BD y obtener el ID generado

        return new PedidosResponseDTO( // Construye el DTO de respuesta a partir del registro ya guardado
                guardado.getIdpedido(), // Obtiene el ID generado por PostgreSQL usando getIdpedido()
                guardado.getImportancia(), // Obtiene la importancia guardada
                guardado.getMetodopago() // Obtiene el método de pago guardado
        ); // Cierra la creación de PedidosResponseDTO
    } // Fin del método crearPedido
} // Fin de la clase