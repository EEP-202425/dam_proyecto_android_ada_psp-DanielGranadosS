package com.tutransporte.carpool.controller;

import com.tutransporte.carpool.model.Usuario;
import com.tutransporte.carpool.repository.UsuarioRepository;
import com.tutransporte.carpool.repository.ViajeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> getById(@PathVariable Long id) {
        return usuarioRepository.findById(id);
    }

    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioRepository.findById(id)
            .map(existing -> {
                usuario.setId(id);
                return usuarioRepository.save(usuario);
            })
            .orElseGet(() -> {
                usuario.setId(id);
                return usuarioRepository.save(usuario);
            });
    }

    @Autowired
    private ViajeRepository viajeRepository;

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable Long id) {
        if (viajeRepository.existsByUsuarioId(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar el usuario porque está asociado a algún viaje.");
        }

        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuarioRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
