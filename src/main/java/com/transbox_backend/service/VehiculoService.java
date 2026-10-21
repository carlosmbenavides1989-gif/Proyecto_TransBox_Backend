package com.transbox_backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.transbox_backend.dto.CambiarEstadoVehiculoRequest;
import com.transbox_backend.dto.CrearVehiculoRequest;
import com.transbox_backend.model.Vehiculo;
import com.transbox_backend.repository.VehiculoRepository;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<Vehiculo> listar() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo crear(CrearVehiculoRequest datos) {
        String placa = datos.placa().trim().toUpperCase();
        if (vehiculoRepository.existsByPlacaIgnoreCase(placa)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La placa ya está registrada");
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(placa);
        vehiculo.setTipo(datos.tipo());
        vehiculo.setMarca(datos.marca());
        vehiculo.setCapacidadKg(datos.capacidadKg());
        vehiculo.setEstado("DISPONIBLE");
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo cambiarEstado(Integer id, CambiarEstadoVehiculoRequest datos) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Vehículo no encontrado"));
        vehiculo.setEstado(datos.estado());
        return vehiculoRepository.save(vehiculo);
    }
}
