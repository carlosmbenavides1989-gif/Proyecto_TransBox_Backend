
package com.transbox_backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.transbox_backend.dto.LoginRequest;
import com.transbox_backend.model.Usuario;
import com.transbox_backend.repository.UsuarioRepository;

@Service
public class AutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AutenticacionService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario verificarCredenciales(LoginRequest datos) {

        // Buscar al usuario por su correo
        Usuario usuario = usuarioRepository.findByCorreo(datos.correo())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales incorrectas"
                ));

        // Verificar la contraseña con BCrypt
        if (usuario.getPasswordHash() == null ||
                !passwordEncoder.matches(
                    datos.contrasena(),
                    usuario.getPasswordHash()
                )) {

            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Credenciales incorrectas"
            );
        }

        // Verificar que la cuenta esté activa
        if (!"ACTIVO".equals(usuario.getEstado())) {

            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Credenciales incorrectas"
            );
        }

        // Devolver el usuario verificado internamente
        return usuario;
    }
}
