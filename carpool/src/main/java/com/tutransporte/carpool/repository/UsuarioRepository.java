package com.tutransporte.carpool.repository;

import com.tutransporte.carpool.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}