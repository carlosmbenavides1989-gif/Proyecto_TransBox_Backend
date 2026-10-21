package com.transbox_backend.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.transbox_backend.dto.CambiarEstadoPedidoRequest;
import com.transbox_backend.dto.CrearPedidoRequest;
import com.transbox_backend.model.Pedido;
import com.transbox_backend.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pedido no encontrado"));
    }

    public Pedido crear(CrearPedidoRequest datos) {
        Pedido pedido = new Pedido();
        pedido.setNumeroPedido(generarNumeroPedido());
        pedido.setClienteNombre(datos.clienteNombre());
        pedido.setDireccionEntrega(datos.direccionEntrega());
        pedido.setDescripcion(datos.descripcion());
        pedido.setEstado("PENDIENTE");
        return pedidoRepository.save(pedido);
    }

    public Pedido cambiarEstado(Integer id, CambiarEstadoPedidoRequest datos) {
        Pedido pedido = buscarPorId(id);
        pedido.setEstado(datos.estado());
        return pedidoRepository.save(pedido);
    }

    private String generarNumeroPedido() {
        String fecha = java.time.LocalDate.now()
                .format(DateTimeFormatter.BASIC_ISO_DATE);
        int consecutivo = ThreadLocalRandom.current().nextInt(1000, 10000);
        return "TBX-" + fecha + "-" + consecutivo;
    }
}
