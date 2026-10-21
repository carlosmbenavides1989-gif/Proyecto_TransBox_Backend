package com.transbox_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transbox_backend.model.Usuario;
import com.transbox_backend.repository.UsuarioRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import com.transbox_backend.dto.ActualizarUsuarioRequest;

import org.springframework.web.bind.annotation.PatchMapping;
import com.transbox_backend.dto.CambiarEstadoUsuarioRequest;

import java.util.Map;
import org.springframework.http.HttpStatus;

import com.transbox_backend.dto.RegistroUsuarioRequest;
import com.transbox_backend.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {


    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public UsuarioController(
            UsuarioRepository usuarioRepository,
            UsuarioService usuarioService) {

        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }


    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Integer id) {
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }

    
    
@PostMapping
public ResponseEntity<Map<String, Object>> crearUsuario(
        @Valid @RequestBody RegistroUsuarioRequest datos) {

    Usuario nuevoUsuario = usuarioService.registrarUsuario(datos);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(Map.of(
                "mensaje", "Usuario registrado correctamente",
                "idUsuario", nuevoUsuario.getIdUsuario(),
                "estado", nuevoUsuario.getEstado()
            ));
}



    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
        @PathVariable Integer id,
        @Valid @RequestBody ActualizarUsuarioRequest datos) {

            return usuarioRepository.findById(id)
                    .map(usuario -> {

                        usuario.setNombres(datos.nombres());
                        usuario.setApellidos(datos.apellidos());
                        usuario.setTelefono(datos.telefono());
                        Usuario actualizado = usuarioRepository.save(usuario);
                        return ResponseEntity.ok(actualizado);
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Usuario> cambiarEstadoUsuario(
                @PathVariable Integer id,
                @Valid @RequestBody CambiarEstadoUsuarioRequest datos) {

            return usuarioRepository.findById(id)
                    .map(usuario -> {

                        usuario.setEstado(datos.estado());
                        Usuario actualizado = usuarioRepository.save(usuario);
                        return ResponseEntity.ok(actualizado);
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
}