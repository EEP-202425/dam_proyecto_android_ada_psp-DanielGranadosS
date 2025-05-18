package com.tutransporte.carpool.controller;

import com.tutransporte.carpool.model.Destino;
import com.tutransporte.carpool.model.Viaje;
import com.tutransporte.carpool.repository.DestinoRepository;
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

    @Autowired
    private DestinoRepository destinoRepository;

    @GetMapping
    public List<Viaje> filtrarViajes(
            @RequestParam(required = false) String destino,
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) Double precio,
            @RequestParam(required = false) Integer plazas,
            @RequestParam(required = false) Long usuarioId
    ) {
        return viajeRepository.findAll().stream()
            .filter(v -> destino == null || 
                (v.getDestino() != null && destino.equalsIgnoreCase(v.getDestino().getCiudad())))
            .filter(v -> fecha == null || fecha.equalsIgnoreCase(v.getFecha()))
            .filter(v -> precio == null || precio.equals(v.getPrecio()))
            .filter(v -> plazas == null || plazas.equals(v.getPlazas()))
            .filter(v -> usuarioId == null || 
                (v.getUsuario() != null && usuarioId.equals(v.getUsuario().getId())))
            .toList();
    }

    @GetMapping("/{id}")
    public Optional<Viaje> getViajeById(@PathVariable Long id) {
        return viajeRepository.findById(id);
    }

    @PostMapping
    public Viaje createViaje(@RequestBody Viaje viaje) {
        if (viaje.getDestino() != null && viaje.getDestino().getId() != null) {
            Long destinoId = viaje.getDestino().getId();
            Destino destinoExistente = destinoRepository.findById(destinoId)
                .orElseThrow(() -> new RuntimeException("Destino no encontrado"));
            viaje.setDestino(destinoExistente);
        }
        return viajeRepository.save(viaje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viaje> updateViaje(@PathVariable Long id, @RequestBody Viaje updatedViaje) {
        return viajeRepository.findById(id)
            .map(v -> {
                updatedViaje.setId(id);
                return ResponseEntity.ok(viajeRepository.save(updatedViaje));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteViaje(@PathVariable Long id) {
        return viajeRepository.findById(id)
            .map(v -> {
                viajeRepository.deleteById(id);
                return ResponseEntity.noContent().build();	
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
