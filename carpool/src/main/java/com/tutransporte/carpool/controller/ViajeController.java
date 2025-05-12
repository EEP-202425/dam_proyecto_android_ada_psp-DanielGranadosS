package com.tutransporte.carpool.controller;

import com.tutransporte.carpool.model.Viaje;
import com.tutransporte.carpool.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/viajes")
public class ViajeController {

    @Autowired
    private ViajeRepository viajeRepository;

    @GetMapping
    public List<Viaje> getAllViajes(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) Long destinoId
    ) {
        if (usuarioId != null) {
            return viajeRepository.findByUsuarioId(usuarioId);
        }

        if (fecha != null && destinoId != null) {
            return viajeRepository.findByFechaAndDestinoId(fecha, destinoId);
        }

        if (fecha != null) {
            return viajeRepository.findByFecha(fecha);
        }

        if (destinoId != null) {
            return viajeRepository.findByDestinoId(destinoId);
        }

        return viajeRepository.findAll();
    }




    @GetMapping("/{id}")
    public Optional<Viaje> getViajeById(@PathVariable Long id) {
        return viajeRepository.findById(id);
    }

    @PostMapping
    public Viaje createViaje(@RequestBody Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viaje> updateViaje(@PathVariable Long id, @RequestBody Viaje updatedViaje) {
        return viajeRepository.findById(id)
            .map(viaje -> {
                viaje.setFecha(updatedViaje.getFecha());
                viaje.setHora(updatedViaje.getHora());
                viaje.setPrecio(updatedViaje.getPrecio());
                viaje.setPlazas(updatedViaje.getPlazas());
                viaje.setUsuario(updatedViaje.getUsuario());
                viaje.setVehiculo(updatedViaje.getVehiculo());
                viaje.setDestino(updatedViaje.getDestino());
                return ResponseEntity.ok(viajeRepository.save(viaje));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
            
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteViaje(@PathVariable Long id) {
        return viajeRepository.findById(id)
            .map(viaje -> {
                viajeRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

}