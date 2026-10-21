package com.transbox_backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.transbox_backend.dto.CrearFranjaRequest;
import com.transbox_backend.model.FranjaEntrega;
import com.transbox_backend.repository.FranjaEntregaRepository;

@Service
public class FranjaEntregaService {

    private final FranjaEntregaRepository franjaRepository;

    public FranjaEntregaService(FranjaEntregaRepository franjaRepository) {
        this.franjaRepository = franjaRepository;
    }

    public List<FranjaEntrega> listar() {
        return franjaRepository.findAll();
    }

    public List<FranjaEntrega> listarDisponibles() {
        return franjaRepository.findByEstadoAndCuposDisponiblesGreaterThan(
                "DISPONIBLE", 0);
    }

    public FranjaEntrega crear(CrearFranjaRequest datos) {
        if (!datos.horaFin().isAfter(datos.horaInicio())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La hora final debe ser posterior a la hora inicial");
        }

        FranjaEntrega franja = new FranjaEntrega();
        franja.setFecha(datos.fecha());
        franja.setHoraInicio(datos.horaInicio());
        franja.setHoraFin(datos.horaFin());
        franja.setCuposDisponibles(datos.cuposDisponibles());
        franja.setEstado("DISPONIBLE");
        return franjaRepository.save(franja);
    }

    public FranjaEntrega reservar(Integer id) {
        FranjaEntrega franja = franjaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Franja no encontrada"));

        if (!"DISPONIBLE".equals(franja.getEstado())
                || franja.getCuposDisponibles() == null
                || franja.getCuposDisponibles() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La franja no tiene cupos disponibles");
        }

        int restantes = franja.getCuposDisponibles() - 1;
        franja.setCuposDisponibles(restantes);
        if (restantes == 0) {
            franja.setEstado("LLENA");
        }
        return franjaRepository.save(franja);
    }
}
