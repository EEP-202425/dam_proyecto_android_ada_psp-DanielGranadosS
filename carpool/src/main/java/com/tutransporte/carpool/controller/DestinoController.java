package com.tutransporte.carpool.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutransporte.carpool.model.Destino;
import com.tutransporte.carpool.repository.DestinoRepository;
import com.tutransporte.carpool.repository.ViajeRepository;


@RestController
@RequestMapping("/api/destinos")
public class DestinoController {

    @Autowired
    private DestinoRepository destinoRepository;

    @GetMapping
    public List<Destino> getAll() {
        return destinoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Destino> getById(@PathVariable Long id) {
        return destinoRepository.findById(id);
    }

    @PostMapping
    public Destino create(@RequestBody Destino destino) {
        return destinoRepository.save(destino);
    }

    @PutMapping("/{id}")
    public Destino update(@PathVariable Long id, @RequestBody Destino destino) {
        return destinoRepository.findById(id)
            .map(existing -> {
                destino.setId(id);
                return destinoRepository.save(destino);
            })
            .orElseGet(() -> {
                destino.setId(id);
                return destinoRepository.save(destino);
            });
    }

    @Autowired
    private ViajeRepository viajeRepository;

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDestino(@PathVariable Long id) {
        if (viajeRepository.existsByDestinoId(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar el destino porque está asociado a algún viaje.");
        }

        return destinoRepository.findById(id)
            .map(destino -> {
                destinoRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }


}