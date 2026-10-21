package com.transbox_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transbox_backend.dto.CambiarEstadoVehiculoRequest;
import com.transbox_backend.dto.CrearVehiculoRequest;
import com.transbox_backend.model.Vehiculo;
import com.transbox_backend.service.VehiculoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    public List<Vehiculo> listar() {
        return vehiculoService.listar();
    }

    @PostMapping
    public ResponseEntity<Vehiculo> crear(
            @Valid @RequestBody CrearVehiculoRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehiculoService.crear(datos));
    }

    @PatchMapping("/{id}/estado")
    public Vehiculo cambiarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody CambiarEstadoVehiculoRequest datos) {
        return vehiculoService.cambiarEstado(id, datos);
    }
}
