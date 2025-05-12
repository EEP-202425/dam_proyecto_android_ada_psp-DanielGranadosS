package com.tutransporte.carpool.controller;

import com.tutransporte.carpool.model.Vehiculo;
import com.tutransporte.carpool.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @GetMapping
    public List<Vehiculo> getAll() {
        return vehiculoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Vehiculo> getById(@PathVariable Long id) {
        return vehiculoRepository.findById(id);
    }

    @PostMapping
    public Vehiculo create(@RequestBody Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    @PutMapping("/{id}")
    public Vehiculo update(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        return vehiculoRepository.findById(id)
            .map(existing -> {
                vehiculo.setId(id);
                return vehiculoRepository.save(vehiculo);
            })
            .orElseGet(() -> {
                vehiculo.setId(id);
                return vehiculoRepository.save(vehiculo);
            });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVehiculo(@PathVariable Long id) {
        return vehiculoRepository.findById(id)
            .map(vehiculo -> {
                vehiculoRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
