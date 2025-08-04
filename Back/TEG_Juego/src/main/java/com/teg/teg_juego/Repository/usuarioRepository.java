package com.teg.teg_juego.Repository;

import com.teg.teg_juego.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface usuarioRepository extends JpaRepository<Usuario, Integer> {
}
