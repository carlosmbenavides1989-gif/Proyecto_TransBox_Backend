package com.transbox_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transbox_backend.dto.CambiarEstadoPedidoRequest;
import com.transbox_backend.dto.CrearPedidoRequest;
import com.transbox_backend.model.Pedido;
import com.transbox_backend.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listar();
    }

    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable Integer id) {
        return pedidoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Pedido> crear(@Valid @RequestBody CrearPedidoRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.crear(datos));
    }

    @PatchMapping("/{id}/estado")
    public Pedido cambiarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody CambiarEstadoPedidoRequest datos) {
        return pedidoService.cambiarEstado(id, datos);
    }
}
