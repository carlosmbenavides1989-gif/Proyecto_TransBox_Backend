package com.transbox_backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** Datos necesarios para registrar una franja de entrega. */
public record CrearFranjaRequest(
    @NotNull(message = "La fecha es obligatoria") LocalDate fecha,
    @NotNull(message = "La hora de inicio es obligatoria") LocalTime horaInicio,
    @NotNull(message = "La hora de fin es obligatoria") LocalTime horaFin,
    @NotNull @Min(value = 1, message = "Debe existir al menos un cupo") Integer cuposDisponibles
) {
}
