package com.tutransporte.carpool.repository;

import com.tutransporte.carpool.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

}