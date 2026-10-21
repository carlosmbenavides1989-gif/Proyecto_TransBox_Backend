package com.transbox_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/** Solicitud para cambiar el estado de un pedido. */
public record CambiarEstadoPedidoRequest(
    @NotBlank
    @Pattern(
        regexp = "PENDIENTE|CONFIRMADO|EN_RUTA|ENTREGADO|SIN_RESPUESTA|RETORNADO",
        message = "Estado de pedido no permitido"
    )
    String estado
) {
}
