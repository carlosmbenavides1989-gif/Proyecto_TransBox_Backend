
package com.transbox_backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.transbox_backend.dto.RegistroUsuarioRequest;
import com.transbox_backend.model.Rol;
import com.transbox_backend.model.Usuario;
import com.transbox_backend.repository.RolRepository;
import com.transbox_backend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(RegistroUsuarioRequest datos) {

        // Verificar correo duplicado
        if (usuarioRepository.existsByCorreo(datos.correo())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "El correo ya está registrado"
            );
        }

        // Verificar documento duplicado
        if (usuarioRepository.existsByDocumento(datos.documento())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "El documento ya está registrado"
            );
        }

        // Buscar el rol Vendedor
        Rol rolVendedor = rolRepository.findByNombreRol("Vendedor")
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No existe el rol Vendedor"
                ));

        // Crear el objeto Usuario
        Usuario usuario = new Usuario();

        usuario.setNombres(datos.nombres());
        usuario.setApellidos(datos.apellidos());
        usuario.setCorreo(datos.correo());
        usuario.setTelefono(datos.telefono());
        usuario.setDocumento(datos.documento());

        // Proteger la contraseña con BCrypt
        usuario.setPasswordHash(
            passwordEncoder.encode(datos.contrasena())
        );

        // Asignar estado y rol inicial
        usuario.setEstado("PENDIENTE");
        usuario.setRol(rolVendedor);

        // Guardar el usuario
        return usuarioRepository.save(usuario);
    }
}
