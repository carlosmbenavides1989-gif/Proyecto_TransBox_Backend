package com.transbox_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/** Solicitud para modificar el estado operativo de un vehículo. */
public record CambiarEstadoVehiculoRequest(
    @NotBlank
    @Pattern(
        regexp = "DISPONIBLE|ASIGNADO|MANTENIMIENTO|INACTIVO",
        message = "Estado de vehículo no permitido"
    )
    String estado
) {
}
