package com.tutransporte.carpool.repository;

import com.tutransporte.carpool.model.Destino;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinoRepository extends JpaRepository<Destino, Long> {

	Optional<Destino> findByCiudadIgnoreCase(String ciudad);

}