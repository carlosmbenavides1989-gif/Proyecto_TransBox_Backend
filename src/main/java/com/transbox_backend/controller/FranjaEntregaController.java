package com.transbox_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transbox_backend.dto.CrearFranjaRequest;
import com.transbox_backend.model.FranjaEntrega;
import com.transbox_backend.service.FranjaEntregaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/franjas")
public class FranjaEntregaController {

    private final FranjaEntregaService franjaService;

    public FranjaEntregaController(FranjaEntregaService franjaService) {
        this.franjaService = franjaService;
    }

    @GetMapping
    public List<FranjaEntrega> listar() {
        return franjaService.listar();
    }

    @GetMapping("/disponibles")
    public List<FranjaEntrega> listarDisponibles() {
        return franjaService.listarDisponibles();
    }

    @PostMapping
    public ResponseEntity<FranjaEntrega> crear(
            @Valid @RequestBody CrearFranjaRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(franjaService.crear(datos));
    }

    @PutMapping("/{id}/reservar")
    public FranjaEntrega reservar(@PathVariable Integer id) {
        return franjaService.reservar(id);
    }
}
