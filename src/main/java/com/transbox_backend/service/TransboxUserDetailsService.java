
package com.transbox_backend.service;

import java.util.Locale;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.transbox_backend.model.Usuario;
import com.transbox_backend.repository.UsuarioRepository;

@Service
public class TransboxUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public TransboxUserDetailsService(
            UsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {

        // Buscar usuario en MySQL
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Credenciales incorrectas"
                ));

        // Comprobar que el usuario esté activo
        if (!"ACTIVO".equals(usuario.getEstado())) {

            throw new UsernameNotFoundException(
                    "Credenciales incorrectas"
            );
        }

        // Obtener el rol del usuario
        String rol = usuario.getRol()
                .getNombreRol()
                .toUpperCase(Locale.ROOT);

        // Entregar los datos a Spring Security
        return User.withUsername(usuario.getCorreo())
                .password(usuario.getPasswordHash())
                .roles(rol)
                .build();
    }
}
