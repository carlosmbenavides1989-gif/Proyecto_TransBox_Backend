
package com.transbox_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroUsuarioRequest(

    @NotBlank(message = "El nombre es obligatorio")
    String nombres,

    @NotBlank(message = "El apellido es obligatorio")
    String apellidos,

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no es válido")
    String correo,

    String telefono,

    @NotBlank(message = "El documento es obligatorio")
    String documento,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 12, message = "La contraseña debe tener mínimo 12 caracteres")
    String contrasena

) {
}
