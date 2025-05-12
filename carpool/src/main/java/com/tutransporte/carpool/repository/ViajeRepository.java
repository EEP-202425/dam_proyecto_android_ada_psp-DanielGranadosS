package com.tutransporte.carpool.repository;

import com.tutransporte.carpool.model.Viaje;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {

	boolean existsByDestinoId(Long id);
	boolean existsByUsuarioId(Long id);
	boolean existsByVehiculoId(Long id);
	List<Viaje> findByUsuarioId(Long usuarioId);
	List<Viaje> findByFecha(String fecha);
	List<Viaje> findByDestinoId(Long destinoId);
	List<Viaje> findByFechaAndDestinoId(String fecha, Long destinoId);



}