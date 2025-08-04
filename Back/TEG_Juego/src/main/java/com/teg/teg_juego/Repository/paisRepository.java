package com.teg.teg_juego.Repository;

import com.teg.teg_juego.model.entities.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface paisRepository extends JpaRepository<Pais, Integer> {
    List<Pais> findAll();
    Optional<Pais> findByNombre(String nombre);
}
