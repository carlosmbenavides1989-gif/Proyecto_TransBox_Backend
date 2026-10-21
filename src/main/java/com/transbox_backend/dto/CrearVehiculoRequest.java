package com.transbox_backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

/** Datos requeridos para registrar un vehículo. */
public record CrearVehiculoRequest(
    @NotBlank(message = "La placa es obligatoria") String placa,
    @NotBlank(message = "El tipo de vehículo es obligatorio") String tipo,
    String marca,
    @DecimalMin(value = "0.01", message = "La capacidad debe ser mayor que cero") BigDecimal capacidadKg
) {
}
