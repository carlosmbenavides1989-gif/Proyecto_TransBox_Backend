package com.transbox_backend.dto;

import jakarta.validation.constraints.NotBlank;

/** Datos requeridos para crear un pedido. */
public record CrearPedidoRequest(
    @NotBlank(message = "El nombre del cliente es obligatorio") String clienteNombre,
    @NotBlank(message = "La dirección de entrega es obligatoria") String direccionEntrega,
    String descripcion
) {
}
