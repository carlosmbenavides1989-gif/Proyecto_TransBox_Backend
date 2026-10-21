package com.transbox_backend.dto;

import jakarta.validation.constraints.NotBlank;

public record ActualizarUsuarioRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    String telefono
) {
}