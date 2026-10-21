package com.transbox_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CambiarEstadoUsuarioRequest(

    @NotBlank
    @Pattern(
        regexp = "ACTIVO|INACTIVO|PENDIENTE",
        message = "El estado debe ser ACTIVO, INACTIVO o PENDIENTE"
    )
    String estado

) {
}