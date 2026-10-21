package com.transbox_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transbox_backend.model.FranjaEntrega;

@Repository
public interface FranjaEntregaRepository extends JpaRepository<FranjaEntrega, Integer> {
    List<FranjaEntrega> findByEstadoAndCuposDisponiblesGreaterThan(String estado, Integer cupos);
}
